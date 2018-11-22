package movinglight;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;



/**
* An example for a moving light source.
*
* @author Frank Klawonn
* Last change 01.07.2005
*/
public class MovingLight extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public MovingLight()
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
    setTitle("A moving light source");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     MovingLight ml = new MovingLight();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

//*** The root of the graph containing the scene (with a cube and a sphere). ***
    BranchGroup theScene = new BranchGroup();


    //Generate an Appearance.
    Color3f ambientColourShaded = new Color3f(1.0f,0.4f,0.4f);
    Color3f emissiveColourShaded = new Color3f(0.0f,0.0f,0.0f);
    Color3f diffuseColourShaded = new Color3f(1.0f,0.0f,0.7f);
    Color3f specularColourShaded = new Color3f(0.0f,0.5f,0.5f);

    float shininessShaded = 20.0f;

    Appearance shadedApp = new Appearance();
    shadedApp.setMaterial(new Material(ambientColourShaded,emissiveColourShaded,
                           diffuseColourShaded,specularColourShaded,shininessShaded));






    float r = 0.3f; //The radius of the sphere.
    float boxHL = 0.7f*r; //Half the vertex length of the cube.
    float shift = 3.0f*r; //Distance between cube and sphere.


//*** The sphere and its transformation group ***
    Sphere s = new Sphere(r,Sphere.GENERATE_NORMALS,100,shadedApp);
    Transform3D tfSphere = new Transform3D();
    tfSphere.setTranslation(new Vector3f(-0.95f+r,0.0f,0.0f));
    TransformGroup tgSphere = new TransformGroup(tfSphere);
    tgSphere.addChild(s);
    theScene.addChild(tgSphere);




//*** The cube and its transformation group ***
    Box b2 = new Box(boxHL,boxHL,boxHL,shadedApp);
    Transform3D tfBox2 = new Transform3D();
    tfBox2.setTranslation(new Vector3f(-0.95f+r+shift,0.0f,0.0f));
    Transform3D rotation = new Transform3D();
    rotation.rotY(Math.PI/4);
    Transform3D rotationX = new Transform3D();
    rotationX.rotX(Math.PI/6);
    rotation.mul(rotationX);
    tfBox2.mul(rotation);
    TransformGroup tgBox2 = new TransformGroup(tfBox2);
    tgBox2.addChild(b2);
    theScene.addChild(tgBox2);




    //Generate a white background.
    Background bg = new Background(new Color3f(1.0f,1.0f,1.0f));
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    bg.setApplicationBounds(bounds);
    theScene.addChild(bg);



    theScene.compile();

    //Add the scene to the universe.
    su.addBranchGraph(theScene);

  }





  //Directional light rotating around the scene and some ambient light.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);


    //Directional light (to be rotated).
    Color3f lightColour = new Color3f(1.0f, 1.0f, 1.0f);
    Vector3f lightDir  = new Vector3f(0.0f, 0.0f, -1.0f);
    DirectionalLight light = new DirectionalLight(lightColour, lightDir);
    light.setInfluencingBounds(bounds);

    //The transformation group for the directional light and its rotation.
    TransformGroup tfmLight = new TransformGroup();
    tfmLight.addChild(light);

    //The Alpha for the rotation.
    Alpha alphaLight = new Alpha(-1,4000);
    //The rotation
    RotationInterpolator rot = new RotationInterpolator(alphaLight,tfmLight,
                                                        new Transform3D(),
                                                         0.0f,(float) Math.PI*2);
    rot.setSchedulingBounds(bounds);

    tfmLight.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tfmLight.addChild(rot);

    bgLight.addChild(tfmLight);




    //Ambient light.
    Color3f ambientLightColour = new Color3f(0.5f, 0.5f, 0.5f);
    AmbientLight ambLight = new AmbientLight(ambientLightColour);
    ambLight.setInfluencingBounds(bounds);
    bgLight.addChild(ambLight);


    su.addBranchGraph(bgLight);
  }



}