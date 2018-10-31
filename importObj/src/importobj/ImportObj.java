package importobj;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author pedro
 */
public class ImportObj extends JFrame {

    /**
     * @param args the command line arguments
     */
    public Canvas3D myCanvas3D;



  public ImportObj()
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
    setTitle("Barcao");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);

  }
    public static void main(String[] args) {
        // TODO code application logic here
        ImportObj staticScene = new ImportObj();
    }
    
    public void createSceneGraph(SimpleUniverse su)
  {
      
        Appearance redApp = new Appearance();
        setToMyDefaultAppearance(redApp,new Color3f(0.8f,01.0f,0.0f));
      
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        Scene s = null;
        try{
            s = f.load("/home/pedro/Downloads/Schiff.obj");
            System.out.println("leu");
        }
        catch (Exception e){
            System.out.println("Nao foi possível ler:" + e);
        }
        
        Hashtable namedObjects = s.getNamedObjects();
        TransformGroup tg = new TransformGroup();
        //System.out.println(s.getNamedObjects());
        Enumeration e = namedObjects.keys();
        while(e.hasMoreElements()){
            //para testar quais objetos tem no arquivo
            //System.out.println((String)e.nextElement());
        
            Shape3D part = (Shape3D) namedObjects.get((String)e.nextElement());
            Shape3D extractedPart = (Shape3D) part.cloneTree();
            extractedPart.setAppearance(redApp);
            
            tg.addChild(extractedPart);
        }
        
        
        BranchGroup theScene = new BranchGroup();
        
        
        theScene.addChild(tg);
        
        Background bg = new Background(new Color3f(01.0f,01.0f,01.0f));
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        bg.setApplicationBounds(bounds);
        theScene.addChild(bg);


        theScene.compile();
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
