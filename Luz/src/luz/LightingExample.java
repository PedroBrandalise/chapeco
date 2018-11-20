package luz;

import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;


/**
 *
 * @author pedro
 */
public class Luz extends JFrame{

    /**
     * @param args the command line arguments
     */
    public Canvas3D myCanvas3D;

    public Luz(){
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
        OrbitBehavior ob = new OrbitBehavior(myCanvas3D, OrbitBehavior.REVERSE_ROTATE);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);


        //Show the canvas/window.
        setTitle("");
        setSize(700,700);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        Luz l = new Luz();
    }
    
    public void createSceneGraph(SimpleUniverse su)
  {

    //A black Appearance to display all objects as wire frame models.
    Appearance blackApp = new Appearance();
    setToMyDefaultAppearance(blackApp,new Color3f(0.80f,0.80f,0.80f));

    //The following lines change the display style of the Appearance blackApp
    //to wire frame instead of solid.
    PolygonAttributes polygAttr = new PolygonAttributes();
    
    blackApp.setPolygonAttributes(polygAttr);




//*** The platform on which the helicopter stands ***
//*** and its transformation group.               ***

    //Half edge length of the cube that represents the platform.
    float platformSize = 0.1f;

    //Generate the platform in the form of a cube.
    Box platform = new Box(platformSize,platformSize,platformSize,blackApp);

    //A transformation rotating the platform a little bit.
    Transform3D tfPlatform = new Transform3D();
    //tfPlatform.rotY(Math.PI/6);

    //The transformation group of the platform.
    TransformGroup tgPlatform = new TransformGroup(tfPlatform);
    tgPlatform.addChild(platform);




    //The transformation placing the helicopter on top of the platform.
    Transform3D tfHelicopter = new Transform3D();
    //tfHelicopter.setTranslation(new Vector3f(0.0f,platformSize+cabinRadius,0.0f));

    //The transformation group for the helicopter.
    TransformGroup tgHelicopter = new TransformGroup(tfHelicopter);
   
    Transform3D tfHeliPlat = new Transform3D();
    tfHeliPlat.setTranslation(new Vector3f(0.0f,0.f,0.0f));

    //The transformation group for the helicopter together with the platform.
    TransformGroup tgHeliPlat = new TransformGroup(tfHeliPlat);
    //tgHeliPlat.addChild(tgHelicopter);
    tgHeliPlat.addChild(tgPlatform);

    BranchGroup theScene = new BranchGroup();


    
    theScene.addChild(tgHeliPlat);
    
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
    
    Color3f cor = new Color3f(1.0f,01.0f,01.0f);
    Point3f local  = new Point3f(0.20f,0.0f,0.0f);
    Point3f atenuacao = new Point3f(0.10f,0.05f,0.0f);
    Vector3f direcao  = new Vector3f(1.0f,0.0f,0.0f);
    
   SpotLight sp = new SpotLight(cor, local, atenuacao , direcao, (float)2.52, 0.0f );
    
    bgLight.addChild(sp);
    //bgLight.addChild(light1);
    su.addBranchGraph(bgLight);
  }
    
}
