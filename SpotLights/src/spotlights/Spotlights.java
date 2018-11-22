package spotlights;


import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import javax.swing.JFrame;
public class Spotlights extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Spotlights sp = new Spotlights();
    }
    
    
    Canvas3D myCanvas3D;

    Color3f red = new Color3f(1.0f, 0.0f, 0.0f);
    Color3f green = new Color3f(0.0f, 1.0f, 0.0f);
    Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);
    Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
    Color3f white = new Color3f(1.0f, 1.0f, 1.0f);


    public Spotlights() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        SimpleUniverse simpleUniv = new SimpleUniverse(myCanvas3D);
        ViewingPlatform viewingPlatform = simpleUniv.getViewingPlatform();

        double fov = simpleUniv.getViewer().getView().getFieldOfView();
        float viewDistance = (float) (4.0 / Math.tan(fov / 2.0));
        Vector3f distanciaObservador = new Vector3f();
        Transform3D transfObservador = new Transform3D();
        distanciaObservador.set(0.0f, 0.0f, viewDistance);
        transfObservador.set(distanciaObservador);
        viewingPlatform.getViewPlatformTransform().setTransform(transfObservador);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        viewingPlatform.setViewPlatformBehavior(ob);

        createSceneGraph(simpleUniv);

        setTitle("Spotlights");
        setSize(700,700);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);

    }
    
    
    public void createSceneGraph(SimpleUniverse su) {

        Material material = new Material(white, black, white, white, 150);
        Appearance appearance = new Appearance();
        appearance.setMaterial(material);

        float aresta = 0.50f;
        Box caixa = new Box(aresta, aresta, aresta, appearance);
        Transform3D tfCaixa = new Transform3D();
        TransformGroup tgCaixa = new TransformGroup(tfCaixa); 
        tgCaixa.addChild(caixa);

        BoundingSphere bounds = new BoundingSphere(new Point3d(), 100.0);
        Transform3D tfSpots = new Transform3D();
        TransformGroup tgSpots = new TransformGroup(tfSpots); 

        Point3f atenuacao = new Point3f(0.0f, 0.1f, 0.0f);
        float angulo = 180; 
        float concentracao = 0.0f;

        float xDistance = 3.0f;
        float yDistance = 3.0f;
        float zDistance = 1.0f;

        Point3f posicaoSpot = new Point3f(-xDistance, yDistance, zDistance);

        Vector3f directionRed = new Vector3f(posicaoSpot);
        SpotLight spotRed = new SpotLight(red, posicaoSpot, atenuacao,
        directionRed, (float) Math.toRadians(angulo),
        concentracao);
        spotRed.setInfluencingBounds(bounds);

        posicaoSpot = new Point3f(xDistance, yDistance, zDistance);

        Vector3f directionGreen = new Vector3f(posicaoSpot);
        SpotLight spotGreen = new SpotLight(green, posicaoSpot, atenuacao,
        directionGreen, (float) Math.toRadians(angulo),
        concentracao);
        spotGreen.setInfluencingBounds(bounds);

        posicaoSpot = new Point3f(0.0f, -yDistance, zDistance);

        Vector3f directionBlue = new Vector3f(posicaoSpot);
        SpotLight spotBlue = new SpotLight(blue, posicaoSpot, atenuacao,
        directionBlue, (float) Math.toRadians(angulo),
        concentracao);
        spotBlue.setInfluencingBounds(bounds);

        tgSpots.addChild(spotRed);
        tgSpots.addChild(spotGreen);
        tgSpots.addChild(spotBlue);

        BranchGroup theScene = new BranchGroup();
        theScene.addChild(tgCaixa);
        theScene.addChild(tgSpots);

        su.addBranchGraph(theScene);

    }
    
}
