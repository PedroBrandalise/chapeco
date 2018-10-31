package javaapplication22;

import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;


/**
* Displaying a scene as a black and white wire frame model.
*
* @author Frank Klawonn
* Last change 27.05.2005
*/
public class TesselationBWExample extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;



  public TesselationBWExample()
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
    OrbitBehavior ob = new OrbitBehavior(myCanvas3D, OrbitBehavior.REVERSE_ROTATE);
    ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
    simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);


    //Show the canvas/window.
    setTitle("Objects as wire frame models");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     TesselationBWExample staticScene = new TesselationBWExample();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

    //A black Appearance to display all objects as wire frame models.
    Appearance blackApp = new Appearance();
    setToMyDefaultAppearance(blackApp,new Color3f(0.0f,0.0f,0.0f));

    //The following lines change the display style of the Appearance blackApp
    //to wire frame instead of solid.
    PolygonAttributes polygAttr = new PolygonAttributes();
    polygAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
    blackApp.setPolygonAttributes(polygAttr);




//*** The platform on which the helicopter stands ***
//*** and its transformation group.               ***

    //Half edge length of the cube that represents the platform.
    float platformSize = 0.1f;

    //Generate the platform in the form of a cube.
    Box platform = new Box(platformSize,platformSize,platformSize,blackApp);

    //A transformation rotating the platform a little bit.
    Transform3D tfPlatform = new Transform3D();
    tfPlatform.rotY(Math.PI/6);

    //The transformation group of the platform.
    TransformGroup tgPlatform = new TransformGroup(tfPlatform);
    tgPlatform.addChild(platform);





//*** The cockpit of the helicopter. ****


    //Radius of the cockpit.
    float cabinRadius = 0.1f;

    //Generate the cockpit in the form of a sphere.
    Sphere cabin = new Sphere(cabinRadius,blackApp);

    //The transformation group for the cockpit.
    //The cockpit first remains in the origin. Later on, the whole
    //helicopter is shifted onto the platform.
    TransformGroup tgCabin = new TransformGroup();
    tgCabin.addChild(cabin);


//*** The rotor blade of the helicopter. ***

    //Generate the rotor blade in the form of a (very thin and long) box.
    Box rotor = new Box(0.4f,0.0001f,0.01f,blackApp);

    //A transformation placing the rotor blade on top of the cockpit.
    Transform3D tfRotor = new Transform3D();
    tfRotor.setTranslation(new Vector3f(0.0f,cabinRadius,0.0f));

    //The transformation group for the rotor blade.
    TransformGroup tgRotor = new TransformGroup(tfRotor);
    tgRotor.addChild(rotor);


//*** The tail of the helicopter. ***

    //Length of the tail.
    float halfTailLength = 0.2f;

    //Generate the tail in form of a box.
    Box tail = new Box(halfTailLength,0.02f,0.02f,blackApp);

    //A transformation placing the tail at the end of the cockpit.
    Transform3D tfTail = new Transform3D();
    tfTail.setTranslation(new Vector3f(cabinRadius+halfTailLength,0.0f,0.0f));

    //The transformation group for the tail.
    TransformGroup tgTail = new TransformGroup(tfTail);
    tgTail.addChild(tail);



    //*** The helicopter, assembled by the transformation groups  ***
    //*** for the cockpit, the rotor blade and the tail.          ***
    //*** Afterwards, the helicopter must be lifted onto the      ***
    //*** platform.                                               ***

    //The transformation placing the helicopter on top of the platform.
    Transform3D tfHelicopter = new Transform3D();
    tfHelicopter.setTranslation(new Vector3f(0.0f,platformSize+cabinRadius,0.0f));

    //The transformation group for the helicopter.
    TransformGroup tgHelicopter = new TransformGroup(tfHelicopter);
    tgHelicopter.addChild(tgCabin);
    //tgHelicopter.addChild(tgRotor);
    tgHelicopter.addChild(tgTail);


    //*** The helicopter and the platform are joint together in one    ***
    //*** transformation group in order to place them together         ***
    //*** in the scene.                                                ***

    //The transformation for positioning the helicopter
    //together with the platform.
    Transform3D tfHeliPlat = new Transform3D();
    tfHeliPlat.setTranslation(new Vector3f(0.0f,0.1f,0.0f));

    //The transformation group for the helicopter together with the platform.
    TransformGroup tgHeliPlat = new TransformGroup(tfHeliPlat);
    tgHeliPlat.addChild(tgHelicopter);
    tgHeliPlat.addChild(tgPlatform);



    //*** The tree trunk. ***

    //Height of the tree trunk.
    float trunkHeight = 0.4f;

    //Generate the tree trunk as a cylinder.
    


    
    //*** The root of the graph containing the scene.  ***
    BranchGroup theScene = new BranchGroup();


    //Add the helicopter and the tree to the scene.
    theScene.addChild(tgHeliPlat);
    


    //The following four lines generate a white background.
    Background bg = new Background(new Color3f(1.0f,1.0f,1.0f));
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