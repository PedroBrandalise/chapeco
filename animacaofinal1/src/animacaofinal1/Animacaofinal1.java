package animacaofinal1;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.image.TextureLoader;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import javax.swing.JFrame;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author pedro
 */
public class Animacaofinal1 extends JFrame{

    
    public Canvas3D myCanvas3D;
    
    public Animacaofinal1(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        
        
        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);
        
        createSceneGraph(simpUniv);
        
        addLight(simpUniv);
        
        //navegação
        OrbitBehavior ob = new OrbitBehavior(myCanvas3D, OrbitBehavior.REVERSE_ROTATE);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);
        
        setTitle("ANIMAÇÂO");
        setSize(700,700);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }   
    
    public static void main(String[] args) {
        Animacaofinal1 af1 = new Animacaofinal1();
        af1.music();
    }

    private void createSceneGraph(SimpleUniverse su) {
        //textura pele
        TextureLoader textureLoad = new TextureLoader("pele.jpg",null);

        ImageComponent2D textureIm = textureLoad.getScaledImage(64,128);

        Texture2D aTexture = new Texture2D(Texture2D.BASE_LEVEL,Texture2D.RGB,
                                                textureIm.getWidth(),
                                                textureIm.getHeight());
        aTexture.setImage(0,textureIm);

        Appearance peleApp = new Appearance();
        peleApp.setTexture(aTexture);
        TextureAttributes textureAttr = new TextureAttributes();
        textureAttr.setTextureMode(TextureAttributes.REPLACE);
        peleApp.setTextureAttributes(textureAttr);
        Material mat = new Material();
        mat.setShininess(120.0f);
        peleApp.setMaterial(mat);
        TexCoordGeneration tcg = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
                                                        TexCoordGeneration.TEXTURE_COORDINATE_2);

        peleApp.setTexCoordGeneration(tcg);

        //textura jeans
        
        TextureLoader textureLoad2 = new TextureLoader("jeans.jpg",null);

        //Generate a (scaled) image of the texture. Height and width must be
        //powers of 2.
        ImageComponent2D textureIm2 = textureLoad2.getScaledImage(64,128);

        //Generate the texture.
        Texture2D aTexture2 = new Texture2D(Texture2D.BASE_LEVEL,Texture2D.RGB,
                                                textureIm2.getWidth(),
                                                textureIm2.getHeight());
        aTexture2.setImage(0,textureIm2);

        //An Appearance which will use the texture.
        Appearance jeanApp = new Appearance();
        jeanApp.setTexture(aTexture2);
        TextureAttributes textureAttr2 = new TextureAttributes();
        textureAttr2.setTextureMode(TextureAttributes.REPLACE);
        jeanApp.setTextureAttributes(textureAttr2);
        Material mat2 = new Material();
        mat2.setShininess(120.0f);
        jeanApp.setMaterial(mat2);
        TexCoordGeneration tcg2 = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR,
                                                        TexCoordGeneration.TEXTURE_COORDINATE_2);

        jeanApp.setTexCoordGeneration(tcg2);

    
    
        //-------------------------------------------------------------------------------------------------
        Appearance redApp = new Appearance();
        
        setToMyDefaultAppearance(redApp, new Color3f(0.0f, 01.0f,01.0f));
        
        Appearance blueApp = new Appearance();
        
        setToMyDefaultAppearance(blueApp, new Color3f(0.0f, 0.0f,0.9f));
        
         
        Appearance whiteApp = new Appearance();
        
        setToMyDefaultAppearance(whiteApp, new Color3f(1.0f, 1.0f,01.0f));
        
        
        float multiplicador = 0.51f;
        
        //cabeça do boneco
        float raioCabeca = 2 * multiplicador;
        
        Sphere cabeca =  new Sphere(raioCabeca, peleApp);
        
        Transform3D tfCabeca = new Transform3D();
        
        TransformGroup tgCabeca = new TransformGroup(tfCabeca);
        tgCabeca.addChild(cabeca);
        
        //corpo do boneco
        float alturaCorpo = 4 * multiplicador;
        
        Box corpo = new Box(raioCabeca,alturaCorpo, raioCabeca/2, whiteApp);
        
        Transform3D tfCorpo = new Transform3D();
        tfCorpo.setTranslation(new Vector3f(0.0f, -3*raioCabeca, 0.0f));
        
        TransformGroup tgCorpo = new TransformGroup(tfCorpo);
        tgCorpo.addChild(corpo);
        
        
        
        //perna direita
        
        float tamPerna = (float) (0.9*(raioCabeca/2));
        Box pernaD = new Box(tamPerna, alturaCorpo, tamPerna, jeanApp );
        
        Transform3D tfPernaD = new Transform3D();
        tfPernaD.setTranslation(new Vector3d(-raioCabeca/2,-(raioCabeca+3*alturaCorpo),0.0));
        
        TransformGroup tgPernaD = new TransformGroup(tfPernaD);
        tgPernaD.addChild(pernaD);
        
        //perna esquerda
        Box pernaE = new Box(tamPerna, alturaCorpo, tamPerna, jeanApp );
        
        Transform3D tfPernaE = new Transform3D();
        tfPernaE.setTranslation(new Vector3d(raioCabeca/2,-(raioCabeca+3*alturaCorpo),0.0));
        
        TransformGroup tgPernaE = new TransformGroup(tfPernaE);
        tgPernaE.addChild(pernaE);
        
        //braço direito 
        
        Box bracoD = new Box(0.7f*tamPerna, alturaCorpo, tamPerna, peleApp );
        
        /*Transform3D tfBracoD =new  Transform3D();
        tfBracoD.setTranslation(new Vector3d(-1.5 *raioCabeca,-1.5*alturaCorpo,0.0f));
        */
        TransformGroup tgBracoD = new TransformGroup();
        tgBracoD.addChild(bracoD);
        
        Transform3D movimentacaoBracoD = new Transform3D();
        movimentacaoBracoD.rotZ(Math.PI/2);
        movimentacaoBracoD.setTranslation(new Vector3d(0.0f,alturaCorpo,0.0f));
        int tempoComecoMV = 2000;
        int numRepeticoes = -1;
        int Duracao = 900;
        
        Alpha alphaMVBD = new Alpha(numRepeticoes, 
                                    Alpha.INCREASING_ENABLE,
                                    tempoComecoMV,
                                    0,Duracao,0,0,0,0,0
        );
        
        RotationInterpolator comecarMovimento = new RotationInterpolator(
                                                            alphaMVBD,
                                                            tgBracoD, 
                                                            movimentacaoBracoD, 
                                                            0.0f, 
                                                            (float) (Math.PI/4));
        
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        comecarMovimento.setSchedulingBounds(bounds);
        
        tgBracoD.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgBracoD.addChild(comecarMovimento);

        Transform3D tfBracoD =new  Transform3D();
        tfBracoD.setTranslation(new Vector3d(-1.5 *raioCabeca,-1.5*alturaCorpo,0.0f));
        TransformGroup tgBracoD2 = new TransformGroup(tfBracoD);
        tgBracoD2.addChild(tgBracoD);
        
        
        //braço esquerdo
        
        Box bracoE = new Box(0.7f*tamPerna, alturaCorpo, tamPerna, peleApp );
        /*Transform3D tfBracoE =new  Transform3D();
        tfBracoE.setTranslation(new Vector3d(1.5 *raioCabeca,-1.5*alturaCorpo,0.0f));
        */
        TransformGroup tgBracoE = new TransformGroup();
        tgBracoE.addChild(bracoE);
        
        
        Transform3D movimentacaoBracoE = new Transform3D();
        movimentacaoBracoE.rotZ(Math.PI/2);
        movimentacaoBracoE.setTranslation(new Vector3d(0.0f,alturaCorpo,0.0f));
        int tempoComecoMV2 = 3000;
        int numRepeticoes2 = -1;
        int Duracao2 = 900;
        
        Alpha alphaMVBE = new Alpha(numRepeticoes2, 
                                    Alpha.INCREASING_ENABLE,
                                    tempoComecoMV2,
                                    0,Duracao2,0,0,0,0,0
        );
        
        RotationInterpolator comecarMovimento2 = new RotationInterpolator(
                                                            alphaMVBE,
                                                            tgBracoE, 
                                                            movimentacaoBracoE, 
                                                            0.0f, 
                                                            (float) (Math.PI/4));
        
        BoundingSphere bounds2 = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        comecarMovimento2.setSchedulingBounds(bounds2);
        
        tgBracoE.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgBracoE.addChild(comecarMovimento2);

        Transform3D tfBracoE =new  Transform3D();
        tfBracoE.setTranslation(new Vector3d(1.5 *raioCabeca,-1.5*alturaCorpo,0.0f));
        TransformGroup tgBracoE2 = new TransformGroup(tfBracoE);
        tgBracoE2.addChild(tgBracoE);

        
        
 //<<---*******************************************************************************************--->>\\       
        //criar e compilar a cena
        BranchGroup theScene = new BranchGroup();
        
        theScene.addChild(tgCorpo);
        theScene.addChild(tgCabeca);
        theScene.addChild(tgPernaD);
        theScene.addChild(tgPernaE);
        theScene.addChild(tgBracoD2);
        theScene.addChild(tgBracoE2);

        Background bg = new Background(new Color3f(0.30f,0.470f,0.80f));
        //BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        bg.setApplicationBounds(bounds);
        theScene.addChild(bg);
        
        theScene.compile();
        
        su.addBranchGraph(theScene);
        
        
    }

    private void addLight(SimpleUniverse su) {
        BranchGroup bgLight = new BranchGroup();

    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
    Color3f lightColour1 = new Color3f(1.0f,1.0f,1.0f);
    Vector3f lightDir1  = new Vector3f(-1.0f,0.0f,-0.5f);
    DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
    light1.setInfluencingBounds(bounds);

    bgLight.addChild(light1);
    su.addBranchGraph(bgLight);
  
    }
    
    public static void setToMyDefaultAppearance(Appearance app, Color3f col)
    {
      app.setMaterial(new Material(col,col,col,col,120.0f));
    }

    
  public void music() {  
        AudioPlayer MGP = AudioPlayer.player;  
        AudioStream BGM;  
        AudioData MD;  
        ContinuousAudioDataStream loop = null;  
 
        try {  
            BGM = new AudioStream(new FileInputStream("som.wav"));  
            MD = BGM.getData();  
            loop = new ContinuousAudioDataStream(MD);  
        } catch(IOException error)  {  
            System.out.println("Error!!!"+error.getMessage());  

        }  
        MGP.start(loop);  
    }
    
}
