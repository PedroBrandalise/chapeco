package textureexemple;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.image.*;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JFrame;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
* An example for using an image as a texture for the surface of an object. The file
* myTexture.jpg is required.
*
* @author Frank Klawonn
* Last change 20.07.2005
*/
public class TextureExample extends JFrame
{


  //The canvas to be drawn upon.
  public Canvas3D myCanvas3D;



  public TextureExample()
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
    setTitle("Use of texture");
    setSize(700,700);
    getContentPane().add("Center", myCanvas3D);
    setVisible(true);


  }




  public static void main(String[] args)
  {
     TextureExample texam = new TextureExample();
     
  }





  //In this method, the objects for the scene are generated and added to 
  //the SimpleUniverse.
  public void createSceneGraph(SimpleUniverse su)
  {

    //Load the image for the texture.
    TextureLoader textureLoad = new TextureLoader("myTexture.jpg",null);

    //Generate a (scaled) image of the texture. Height and width must be
    //powers of 2.
    ImageComponent2D textureIm = textureLoad.getScaledImage(64,128);

    //Generate the texture.
    Texture2D aTexture = new Texture2D(Texture2D.BASE_LEVEL,Texture2D.RGB,
                                            textureIm.getWidth(),
                                            textureIm.getHeight());
    aTexture.setImage(0,textureIm);

    //An Appearance which will use the texture.
    Appearance textureApp = new Appearance();
    textureApp.setTexture(aTexture);
    TextureAttributes textureAttr = new TextureAttributes();
    textureAttr.setTextureMode(TextureAttributes.REPLACE);
    textureApp.setTextureAttributes(textureAttr);
    Material mat = new Material();
    mat.setShininess(120.0f);
    textureApp.setMaterial(mat);
    TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
                                                    TexCoordGeneration.TEXTURE_COORDINATE_2);

    textureApp.setTexCoordGeneration(tcg);


    //A sphere to which Appearance, i.e. the texture is assigned.
    Sphere tSphere = new Sphere(0.5f,Sphere.GENERATE_NORMALS,100,textureApp);

    //The transformation group for the sphere.
    TransformGroup tgSphere = new TransformGroup();
    tgSphere.addChild(tSphere);

    //Add everything to the scene.
    BranchGroup theScene = new BranchGroup();
    theScene.addChild(tgSphere);


    theScene.compile();

    //Add the scene to the universe.
    su.addBranchGraph(theScene);

  }







  //Some light is added to the scene here.
  public void addLight(SimpleUniverse su)
  {

    BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE);
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(0.2f,-0.1f,-1.0f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);

    bgLight.addChild(light1);


    su.addBranchGraph(bgLight);

  }
  
  public static void music() {  
        AudioPlayer MGP = AudioPlayer.player;  
        AudioStream BGM;  
        AudioData MD;  
        ContinuousAudioDataStream loop = null;  

        try {  
            BGM = new AudioStream(new FileInputStream("som.wav"));  
            MD = BGM.getData();  
            loop = new ContinuousAudioDataStream(MD);  
        } catch(IOException error)  {  
            System.out.println("Error!!!");  

        }  
        MGP.start(loop);  
    }


}