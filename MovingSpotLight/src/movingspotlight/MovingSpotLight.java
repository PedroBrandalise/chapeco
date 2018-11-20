package movingspotlight;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;



/**
* An unrealistic effect caused by a rotating spotlight whose light
* intensity does not decrease from the centre of the light cone to
* the edge of the light cone. The intensity drops abruptly to 0 at 
* the edge of the light cone. Changing the last parameter in the
* constructor of the SpotLight from 0.0f to 1.0f will amend this 
* effect.
*
* @author Frank Klawonn
* Last change 01.07.2005
*/
public class MovingSpotLight extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public MovingSpotLight()
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
    setTitle("A moving light spotlight");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     MovingSpotLight msl = new MovingSpotLight();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

//*** The root of the graph containing the scene (with a cube and a sphere). ***
    BranchGroup theScene = new BranchGroup();


    //Generate an Appearance.
    Color3f ambientColourShaded = new Color3f(0.0f,0.4f,0.4f);
    Color3f emissiveColourShaded = new Color3f(0.0f,0.0f,0.0f);
    Color3f diffuseColourShaded = new Color3f(0.0f,0.7f,0.7f);
    Color3f specularColourShaded = new Color3f(0.0f,0.5f,0.5f);

    float shininessShaded = 20.0f;

    Appearance shadedApp = new Appearance();
    shadedApp.setMaterial(new Material(ambientColourShaded,emissiveColourShaded,
                           diffuseColourShaded,specularColourShaded,shininessShaded));






    float r = 0.3f; //The radius of the sphere.
    float boxHL = 0.7f*r; //Half the vertex length of the cube.
    float shift = 3.0f*r; //Distance between cube and sphere.




//*** The cube and its transformation group ***
    Box b2 = new Box(boxHL,boxHL,boxHL,shadedApp);
    Transform3D tfBox2 = new Transform3D();
    /*tfBox2.setTranslation(new Vector3f(-0.95f+r+shift,0.0f,0.0f));
    Transform3D rotation = new Transform3D();
    //rotation.rotY(Math.PI/4);
    Transform3D rotationX = new Transform3D();
    rotationX.rotX(Math.PI/6);
    rotation.mul(rotationX);
    tfBox2.mul(rotation);
    */
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





  //Add light to the scene including the rotating spotlight.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);


    //A spotlight rotating around the scene.
    Color3f lightColourSpot = new Color3f(0.0f, 0.0f, 1.0f);
    
    SpotLight lightSpot = new SpotLight(lightColourSpot,        //COR 
                                     new Point3f(0.0f,0.0f,0.70f), //local
                                     new Point3f(0.1f,0.1f,0.01f),
                                     new Vector3f(0.0f,0.0f,-1.0f),
                                     (float) (Math.PI/4),
                                     0.0f);

    lightSpot.setInfluencingBounds(bounds);
    
        Color3f lightColourSpot2 = new Color3f(1.0f, 0.0f, 0.0f);

    SpotLight lightSpot2 = new SpotLight(lightColourSpot2,        //COR 
                                     new Point3f(0.0f,0.990f,0.00f), //local
                                     new Point3f(0.1f,0.1f,0.01f),
                                     new Vector3f(0.0f,1.0f,0.0f),
                                     (float) (Math.PI/4),
                                     0.0f);

    lightSpot2.setInfluencingBounds(bounds);


    //The transformation group for the spotlight and its rotation.
    TransformGroup tfmLight = new TransformGroup();
    tfmLight.addChild(lightSpot);
    tfmLight.addChild(lightSpot2);

    //The Alpha for the rotation.
    Alpha alphaLight = new Alpha(-1 , 4000);
    
    //The rotation
    RotationInterpolator rot = new RotationInterpolator(alphaLight,tfmLight,
                                                        new Transform3D(),
                                                         0.0f,(float) Math.PI*2);
    rot.setSchedulingBounds(bounds);

    tfmLight.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    //tfmLight.addChild(rot);

    bgLight.addChild(tfmLight);




    //Ambient light.
    Color3f ambientLightColour = new Color3f(0.5f, 0.5f, 0.5f);
    AmbientLight ambLight = new AmbientLight(ambientLightColour);
    ambLight.setInfluencingBounds(bounds);
    bgLight.addChild(ambLight);


    su.addBranchGraph(bgLight);
  }



}