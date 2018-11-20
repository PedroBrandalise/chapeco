package simpleanimation3d;



import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;


/**
* A simple example for an animated scene.
*
* @author Frank Klawonn
* Last change 27.05.2005
* @see StaticSceneExample
*/
public class SimpleAnimation3d extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public SimpleAnimation3d()
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
    setTitle("A simple animation");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     SimpleAnimation3d sa = new SimpleAnimation3d();
  }


  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

//*** The platform on which the helicopter stands ***
//*** and its transformation group.               ***

      //An Appearance to make the platform red.
      Appearance redApp = new Appearance();
      setToMyDefaultAppearance(redApp,new Color3f(0.8f,0.0f,0.0f));


      //Half edge length of the cube that represents the platform.
      float platformSize = 0.1f;

      //Generate the platform in the form of a cube.
      Box platform = new Box(platformSize,platformSize,platformSize,redApp);

      //A transformation rotating the platform a little bit.
      Transform3D tfPlatform = new Transform3D();
      tfPlatform.rotY(Math.PI/6);

      //The transformation group of the platform.
      TransformGroup tgPlatform = new TransformGroup(tfPlatform);
      tgPlatform.addChild(platform);


//*** The cockpit of the helicopter. ****

      //An Appearance to make the cockpit green.
      Appearance greenApp = new Appearance();
      setToMyDefaultAppearance(greenApp,new Color3f(0.0f,0.7f,0.0f));

      //Radius of the cockpit.
      float cabinRadius = 0.1f;

      //Generate the cockpit in the form of a sphere.
      Sphere cabin = new Sphere(cabinRadius,greenApp);

      //The transformation group for the cockpit.
      //The cockpit first remains in the origin. Later on, the whole
      //helicopter is shifted onto the platform.
      
      TransformGroup tgCabin = new TransformGroup();
      tgCabin.addChild(cabin);




//*** The rotor blade of the helicopter with its rotation ***

    //An Appearance to make the rotor blade blue.
    Appearance blueApp = new Appearance();
    setToMyDefaultAppearance(blueApp,new Color3f(0.0f,0.0f,1.0f));

    //Generate the rotor blade in the form of a (very thin and long) box.
    Box rotor = new Box(0.4f,0.0001f,0.01f,blueApp);

    //The transformation group for then rotor blade in which also the 
    //animated rotation is implemented.
    TransformGroup tgmRotor = new TransformGroup();
    tgmRotor.addChild(rotor);

    //The slow rotation at the beginning.
    Transform3D bladeRotationAxis = new Transform3D();
    
    int timeStartRotor = 2000;  //The rotor blade should start to turn after 2 seconds.
    int noStartRotations = 2;   //First, two slow rotations are carried out.
    int timeSlowRotation = 1500;//A slow rotation takes 1.5 seconds.

    //The Alpha for the slow rotation.
    Alpha bladeRotationStartAlpha = new Alpha(noStartRotations,
                                              Alpha.INCREASING_ENABLE,
                                              timeStartRotor,
                                              0,timeSlowRotation,0,0,0,0,0);



    //The slow rotation.
    RotationInterpolator bladeRotationStart = new RotationInterpolator(
                                             bladeRotationStartAlpha,tgmRotor,
                                             bladeRotationAxis,0.0f,(float) Math.PI*2);

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
    bladeRotationStart.setSchedulingBounds(bounds);

//---------------------------------------------------------------------------------------------------

    int timeFastRotation = 500; //A fast rotation takes 0.5 seconds.
    int timeOneWayFlight = 2000;//The helicopter rises within 2 seconds.
    int timeHovering = 1000;//It shall hover for one second.
    int timeStartWait = 1000;//The helicopter should start its flight when the rotor
                             //blade has been rotating fast for one second.

    //The overall time when the helicopter should start its flight.
    int timeFlightStart = timeStartRotor+timeSlowRotation*noStartRotations+timeStartWait;
    //Number of fast rotations.
    int noFastRotations = 1+ ((timeStartWait+2*timeOneWayFlight+timeHovering)/timeFastRotation);

    //The Alpha for the fast rotations.
    Alpha bladeRotationAlpha = new Alpha(noFastRotations,Alpha.INCREASING_ENABLE,
                                         timeStartRotor+timeSlowRotation*noStartRotations,
                                         0,timeFastRotation,0,0,0,0,0);


    //The fast rotation.
    RotationInterpolator bladeRotation = new RotationInterpolator(
                                             bladeRotationAlpha,tgmRotor,
                                             bladeRotationAxis,0.0f,(float) Math.PI*2);
    bladeRotation.setSchedulingBounds(bounds);


    //The slow and the fast rotation are assigned to the transformation group
    //of the rotor blade. These rotations are carried out in the origin so far.
    //The later transformations will place everything correctly.
    tgmRotor.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tgmRotor.addChild(bladeRotationStart);
    tgmRotor.addChild(bladeRotation);


//*** The transformation group to position the rotor blade on top of the cockpit. ***
    //The rotation of the rotor blade will therefore also take place om top
    //of the cockpit.
    Transform3D tfRotor = new Transform3D();
    tfRotor.setTranslation(new Vector3f(0.0f,cabinRadius,0.0f));
    TransformGroup tgRotor = new TransformGroup(tfRotor);
    tgRotor.addChild(tgmRotor);



//*** The tail of the helicopter. ***

    //Length of the tail.
    float halfTailLength = 0.2f;

    //Generate the tail in form of a green box.
    Box tail = new Box(halfTailLength,0.02f,0.02f,greenApp);

    //A transformation placing the tail at the end of the cockpit.
    Transform3D tfTail = new Transform3D();
    tfTail.setTranslation(new Vector3f(cabinRadius+halfTailLength,0.0f,0.0f));

    //The transformation group for the tail.
    TransformGroup tgTail = new TransformGroup(tfTail);
    tgTail.addChild(tail);



//*** The transformation group for the flight of the helicopter. ***
    //The flight is carried out with respect to the origin. Later on, the helicopter
    //including its movement will be placed onto the platform.
    TransformGroup tgmHelicopter = new TransformGroup();

    //Add all parts of the helicopter.
    tgmHelicopter.addChild(tgCabin);
    tgmHelicopter.addChild(tgRotor);
    tgmHelicopter.addChild(tgTail);

    //Define the movement for the flight.
    int timeAcc = 300; //The acceleration and breaking phase should last 0.3 seconds.
    //The helicopter fly slightly of from the vertical axis.
    Transform3D helicopterFlightAxis = new Transform3D();
    helicopterFlightAxis.rotZ(0.4*Math.PI);

    //The Alpha for the flight of the helicopter.
    Alpha helicopterAlpha = new Alpha(1,Alpha.INCREASING_ENABLE+Alpha.DECREASING_ENABLE,
                                      timeFlightStart,0,timeOneWayFlight,timeAcc,
                                      timeHovering,timeOneWayFlight,timeAcc,0);



    //The movement for the flight.
    PositionInterpolator posIPHelicopter = new PositionInterpolator(helicopterAlpha,
                                                   tgmHelicopter,helicopterFlightAxis,
                                                   0.0f,0.5f);

    posIPHelicopter.setSchedulingBounds(bounds);

    //Add the movement for the flight to the transformation group of the helicopter.
    tgmHelicopter.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tgmHelicopter.addChild(posIPHelicopter);


//*** The transformation group for placing the helicopter on top of the platform.
    Transform3D tfHelicopter = new Transform3D();
    tfHelicopter.setTranslation(new Vector3f(0.0f,platformSize+cabinRadius,0.0f));
    TransformGroup tgHelicopter = new TransformGroup(tfHelicopter);
    tgHelicopter.addChild(tgmHelicopter);





//*** The helicopter and the platform are joint together in one    ***
//*** transformation group in order to place them together         ***
//*** in the scene.                                                ***
    Transform3D tfHeliPlat = new Transform3D();
    tfHeliPlat.setTranslation(new Vector3f(0.0f,0.1f,0.0f));
    TransformGroup tgHeliPlat = new TransformGroup(tfHeliPlat);
    tgHeliPlat.addChild(tgHelicopter);
    tgHeliPlat.addChild(tgPlatform);


//*** The tree trunk. ***

    //An Appearance to make the tree trunk brown.
    Appearance brownApp = new Appearance();
    setToMyDefaultAppearance(brownApp,new Color3f(0.5f,0.2f,0.2f));

    //Height of the tree trunk.
    float trunkHeight = 0.4f;

    //Generate the tree trunk as a cylinder.
    Cylinder trunk = new Cylinder(0.05f,trunkHeight,brownApp);

    //The transformation group of the tree trunk. The tree trunk first
    //remains in the origin and is placed in the scene together with the 
    //leaves.
    TransformGroup tgTrunk = new TransformGroup();
    tgTrunk.addChild(trunk);


//*** The treetop. ***

    //Height of the treetop.
    float leavesHeight = 0.4f;

    //Generate the treetop in the form of a green cone.
    Cone leaves = new Cone(0.3f,leavesHeight,greenApp);

    //A transformation to place the treetop on top of the tree trunk.
    Transform3D tfLeaves = new Transform3D();
    tfLeaves.setTranslation(new Vector3f(0.0f,(trunkHeight+leavesHeight)/2,0.0f));

    //The transformation group of the treetop.
    TransformGroup tgLeaves = new TransformGroup(tfLeaves);
    tgLeaves.addChild(leaves);


//*** The tree assembled from the transformation groups for     ***
//*** the tree trunk and the treetop.                           ***

    //A transformation for positioning the tree in the scene.
    Transform3D tfTree = new Transform3D();
    tfTree.setTranslation(new Vector3f(-0.6f,0.0f,0.0f));

    //The transformation group of the tree.
    TransformGroup tgTree = new TransformGroup(tfTree);
    tgTree.addChild(tgTrunk);
    tgTree.addChild(tgLeaves);






//*** The root of the graph containing the scene. ***
    BranchGroup theScene = new BranchGroup();


    //Add the helicopter and the tree to the scene.
    theScene.addChild(tgHeliPlat);
    theScene.addChild(tgTree);



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