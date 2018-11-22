package shading;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;



/**
* A sphere rendered with constant shading using different tesselations.
*
* @author Frank Klawonn
* Last change 01.07.2005
*
* @see TesselationResolution
*/
public class ShadingExample extends JFrame
{

  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;


  public ShadingExample()
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
    setTitle("Sphere rendered with constant shading in different tesselations");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }




  public static void main(String[] args)
  {
     ShadingExample se = new ShadingExample();
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

    //Create the root of the branch graph
    BranchGroup theScene = new BranchGroup();


    //Generate an Appearance for the spheres.
    Color3f ambientColourSphere = new Color3f(0.2f,0.2f,0.2f);
    Color3f emissiveColourSphere = new Color3f(0.0f,0.0f,0.0f);
    Color3f diffuseColourSphere = new Color3f(0.6f,0.6f,0.6f);
    Color3f specularColourSphere = new Color3f(0.5f,0.5f,0.5f);

    float shininessSphere = 20.0f;

    Appearance sphereApp = new Appearance();

    sphereApp.setMaterial(new Material(ambientColourSphere,emissiveColourSphere,
                           diffuseColourSphere,specularColourSphere,shininessSphere));

    ColoringAttributes ca = new ColoringAttributes();
    ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
    sphereApp.setColoringAttributes(ca);


    //n spheres with radius r will be shown.
    int n = 5;
    float r = 0.15f;
    float shift = 2*r+0.05f;//The distance between the centres of the spheres.

    //Arrays for the sphere, their transformations and their transformation groups
    //transformation groups (for positioning).
    Sphere[] spheres = new Sphere[n];
    TransformGroup[] tg = new TransformGroup[n];
    Transform3D[] tf = new Transform3D[n];

    //Generate the sphere, their transformations and their
    //transformation groups. Add everyting to the scene.
    for (int i=0; i<n; i++)
    {
      spheres[i] = new Sphere(r,Sphere.GENERATE_NORMALS,4+i*i*i,sphereApp);
      tf[i] = new Transform3D();
      tf[i].setTranslation(new Vector3f(-0.95f+r+shift*i,0.0f,0.0f));
      tg[i] = new TransformGroup(tf[i]);
      tg[i].addChild(spheres[i]);
      theScene.addChild(tg[i]);
    }


    //Generate a white background.
    Background bg = new Background(new Color3f(1.0f,1.0f,1.0f));
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),1000.0);
    bg.setApplicationBounds(bounds);
    theScene.addChild(bg);



    theScene.compile();

    //Add the scene to the universe
    su.addBranchGraph(theScene);

  }





  //Some light is added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);


    //Directional light.
    Color3f lightColour = new Color3f(1.0f, 1.0f, 1.0f);
    Vector3f lightDir  = new Vector3f(1.0f, -1.0f, -1.0f);
    DirectionalLight light = new DirectionalLight(lightColour, lightDir);
    light.setInfluencingBounds(bounds);
    bgLight.addChild(light);



    //Ambient light.
    Color3f ambientLightColour = new Color3f(0.5f, 0.5f, 0.5f);
    AmbientLight ambLight = new AmbientLight(ambientLightColour);
    ambLight.setInfluencingBounds(bounds);
    bgLight.addChild(ambLight);


    su.addBranchGraph(bgLight);
  }

}