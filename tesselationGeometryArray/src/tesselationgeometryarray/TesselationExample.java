package tesselationgeometryarray;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;


/**
* Parts of a scene are displayed as a wire frame model.
*
* @author Frank Klawonn
* Last change 27.05.2005
*/
public class TesselationExample extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;



  public TesselationExample()
  {
    //Mechanism for closing the window and ending the program.
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    //Default settings for the viewer parameters.
    myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());


    //Construct the SimpleUniverse:
    //First generate it using the Canvas.
    SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);


    //Default position of the viewer.
    simpUniv.getViewingPlatform().setNominalViewingTransform();


    //The scene is generated in this method.
    createSceneGraph(simpUniv);


    //Add some light to the scene.
    addLight(simpUniv);


    //The following three lines enable navigation through the scene using the mouse.
    OrbitBehavior ob = new OrbitBehavior(myCanvas3D, OrbitBehavior.REVERSE_ALL);
    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);


    //Show the canvas/window.
    setTitle("Some objects as wire frame models");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     TesselationExample staticScene = new TesselationExample();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {
        Point3f[] vertexCoordinates =
        {
        new Point3f(0,0,0.2f),
        new Point3f(0,0.30f,0),
        new Point3f(0.3f,0.3f,0),
        new Point3f(0.3f,0.3f,0.3f)
        };
        
        int triangles[] = {
            1,3,0, //invertido pra mostrar q a face de dentro pra fora n tem textura
            0,2,3,
            0,1,2,
            1,3,2
        };
        
        
        GeometryInfo gi = new GeometryInfo( GeometryInfo.TRIANGLE_ARRAY );
        gi.setCoordinates(vertexCoordinates);
        gi.setCoordinateIndices(triangles);
        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);
        GeometryArray ga = gi.getGeometryArray();
        
        Appearance redApp = new Appearance();
        setToMyDefaultAppearance(redApp,new Color3f(1.0f,0.8f,0.0f));
        
        Shape3D myShape = new Shape3D(ga,redApp);
        
        TransformGroup tgMyShape = new TransformGroup();
        
        Sphere s = new Sphere(0.10f,redApp);
        
        tgMyShape.addChild(myShape);
        
        
        BranchGroup theScene = new BranchGroup();
        //Add the helicopter and the tree to the scene.
        theScene.addChild(tgMyShape);
        //theScene.addChild(tgTree);
//        theScene.addChild(s);

        //The following four lines generate a white background.
        Background bg = new Background(new Color3f(0.0f,0.0f,0.0f));
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        bg.setApplicationBounds(bounds);
        theScene.addChild(bg);


        theScene.compile();

        //Add the scene to the universe.
        su.addBranchGraph(theScene);
  }


  /**
  * Generates a default surface (Appearance) in a specified colour.
  *
  * @param app      The Appearance for the surface.
  * @param col      The colour.
  */
  public static void setToMyDefaultAppearance(Appearance app, Color3f col)
  {
    app.setMaterial(new Material(col,col,col,col,120.0f));
  }



  //Some light is added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);

    bgLight.addChild(light1);
    su.addBranchGraph(bgLight);
  }


}