package normalformproj;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;



/**
* An example for constructing an object (tetrahedron) with triangles
* where normal vectors are interpolated.
*
* @author Frank Klawonn
* Last change 05.07.2005
* @see GeomArrayExample
*/
public class NormalsForGeomArrays extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public NormalsForGeomArrays()
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
    OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);


    //Show the canvas/window.
    setTitle("Statische Szene aus elementaren Objekten");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     NormalsForGeomArrays nfga = new NormalsForGeomArrays();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {


    Appearance yellowApp = new Appearance();
    setToMyDefaultAppearance(yellowApp,new Color3f(0.5f,0.5f,0.0f));


    //The four vertices of the tetrahedron.
    Point3f[] tetrahedronCoordinates =
      {
        new Point3f(0.5f,0.0f,0.0f),
        new Point3f(-0.5f,0.0f,0.0f),
        new Point3f(0.0f,0.0f,-0.6f),
        new Point3f(0.0f,0.7f,0.0f)
      };


    //The four faces (triangles) of the tetrahedron.
    int coordIndices[] =
      {
        3,0,1,
        0,2,3,
        0,1,2,
        1,3,2
      };




    GeometryInfo gi = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);
    gi.setCoordinates(tetrahedronCoordinates);
    gi.setCoordinateIndices(coordIndices);
    NormalGenerator ng = new NormalGenerator();

//The parameter specifying up to which angle normal vectors should be 
//interpolated, is se here.
    ng.setCreaseAngle(Math.PI);

    ng.generateNormals(gi);
    GeometryArray te = gi.getGeometryArray();

    //Generate the tetrahedron as a Shape.
    Shape3D tetrahedron = new Shape3D(te,yellowApp);

    //The transformation group of the tetrahedron.
    //The tetrahedron will be rotated a little bit.
    Transform3D tfTetrahedron = new Transform3D();
    tfTetrahedron.rotX(0.4*Math.PI);
    TransformGroup tgTetrahedron = new TransformGroup(tfTetrahedron);
    tgTetrahedron.addChild(tetrahedron);


//*** The root of the scenegraph. ***
    BranchGroup theScene = new BranchGroup();

    //Add the tetrahedron to the scene.
    theScene.addChild(tgTetrahedron);

    theScene.compile();

    //Add everything to the universe.
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

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);


    Vector3f lightDir2  = new Vector3f(1.0f,0.0f,0.5f);
    DirectionalLight light2 = new DirectionalLight(lightColour1, lightDir2);
    light2.setInfluencingBounds(bounds);


    bgLight.addChild(light1);
    bgLight.addChild(light2);

    su.addBranchGraph(bgLight);
  }



}