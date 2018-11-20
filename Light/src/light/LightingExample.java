package light;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;


public class LightingExample extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public LightingExample()
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
    setTitle("Various illumination effects");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     LightingExample le = new LightingExample();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

//*** Generate a cube. ***

    //Generate an Appearance for the cube.
    Color3f ambientColourBox = new Color3f(0.8f,0.0f,0.0f);
    Color3f emissiveColourBox = new Color3f(0.0f,0.0f,0.0f);
    Color3f diffuseColourBox = new Color3f(0.8f,0.0f,0.0f);
    Color3f specularColourBox = new Color3f(1.0f,0.0f,0.0f);
    float shininessBox = 10.0f;

    Appearance boxApp = new Appearance();


    boxApp.setMaterial(new Material(ambientColourBox,emissiveColourBox,
                          diffuseColourBox,specularColourBox,shininessBox));




    Box myBox = new Box(0.2f,0.2f,0.2f,boxApp);

    //Rotate and shift the cube slightly.
    Transform3D tfBox = new Transform3D();
    //tfBox.rotY(Math.PI/6);
    //tfBox.rotX(Math.PI/9);
    Transform3D shift = new Transform3D();
    shift.setTranslation(new Vector3f(-0.6f,-0.2f,0.1f));
    //tfBox.mul(shift);
    TransformGroup tgBox = new TransformGroup(tfBox);
    tgBox.addChild(myBox);




//***Generate a sphere ***

    //Generate an Appearance for the sphere.
    Color3f ambientColourSphere = new Color3f(0.2f,0.2f,0.0f);
    Color3f emissiveColourSphere = new Color3f(0.0f,0.0f,0.0f);
    Color3f diffuseColourSphere = new Color3f(0.4f,0.4f,0.0f);
    Color3f specularColourSphere = new Color3f(0.8f,0.8f,0.0f);
    float shininessSphere = 120.0f;

    Appearance sphereApp = new Appearance();

    sphereApp.setMaterial(new Material(ambientColourSphere,emissiveColourSphere,
                           diffuseColourSphere,specularColourSphere,shininessSphere));



    Sphere mySphere = new Sphere(0.3f,Sphere.GENERATE_NORMALS,100,sphereApp);
    Transform3D tfSphere = new Transform3D();
    tfSphere.setTranslation(new Vector3f(0.0f,0.0f,-1.0f));

    TransformGroup tgSphere = new TransformGroup(tfSphere);
    tgSphere.addChild(mySphere);




    //*** Generate (the root of) the scenegraph. ***
    BranchGroup theScene = new BranchGroup();


    //Add the cube and the sphere to the scene.
    theScene.addChild(tgBox);
    theScene.addChild(tgSphere);


    //Generate a white background.
    Background bg = new Background(new Color3f(1.0f,1.0f,1.0f));
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    bg.setApplicationBounds(bounds);
    theScene.addChild(bg);




    theScene.compile();

    //Add everything to the universe.
    su.addBranchGraph(theScene);

  }





  //The different light sources are added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);




    //Light no. 1: directional light.
    Color3f lightColour1 = new Color3f(0.8f, 0.8f, 0.8f);
    Vector3f lightDir1  = new Vector3f(0.0f, 4.0f, -1.0f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);
    //bgLight.addChild(light1);



    //Light no. 2: a point light.
    Color3f lightColour2 = new Color3f(0.3f, 0.3f, 0.3f);
    PointLight light2 = new PointLight(lightColour2,
                                       new Point3f(1.0f,1.0f,1.0f),
                                       new Point3f(0.2f,0.01f,0.01f));
    light2.setInfluencingBounds(bounds);
    //bgLight.addChild(light2);


    //Light no. 3: a spotlight.
    Color3f lightColour3 = new Color3f(0.0f, 01.0f, 0.3f);
    SpotLight light3 = new SpotLight(lightColour3,
                                     new Point3f(0.0f,0.0f,0.0f),
                                     new Point3f(0.1f,0.1f,0.01f),
                                     new Vector3f(0.0f,0.0f,-1.0f),
                                     (float) (Math.PI),
                                     0.0f);

    light3.setInfluencingBounds(bounds);
    //bgLight.addChild(light3);



    //Light no. 4: ambient light.
    Color3f lightColour4 = new Color3f(0.2f, 0.2f, 0.2f);
    AmbientLight light4 = new AmbientLight(lightColour4);
    light4.setInfluencingBounds(bounds);
    //bgLight.addChild(light4);


    su.addBranchGraph(bgLight);
  }



}