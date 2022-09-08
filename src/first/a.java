package first;

import javax.swing.*;


class Slider extends JSlider{
    public Slider(){
        super(0,100);
    }

    synchronized public void Increase(int increment){
        setValue((int)getValue()+increment);
    }
}

class myThread extends Thread{
    private int increment;
    private Slider slider;
    private int count;
    private static int BORDER= 500000;
    private static int THREAD_COUNTER =0;
    private int curNum;
    public myThread(Slider slider, int increment, int priority){
        this.slider= slider;
        this.increment=increment;
        curNum=++THREAD_COUNTER;
        setPriority(priority);
    }

    @Override
    public void run(){
        while(!interrupted()){
            int value=(int)(slider.getValue());
            ++count;
            if(count>BORDER){
                slider.Increase(increment);
                count=0;
            }
        }
    }

    public JPanel GetJPanel(){
        JPanel panel= new JPanel();
        JLabel label= new JLabel("Thread № "+ curNum+ " Increment = "+ increment);
        SpinnerModel sModel=new SpinnerNumberModel(getPriority(),Thread.MIN_PRIORITY,Thread.MAX_PRIORITY,1);
        JSpinner spinner= new JSpinner((sModel));
        panel.add (label);
        panel.add(spinner);
        return panel;}

}


public class a {
    public static int SEMAPHORE =0;
    public static void main(String[] args){
        JFrame first_lab=new JFrame();
        first_lab.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        first_lab.setSize(600,600);
        Slider slider= new Slider();

        myThread thread1=new myThread(slider,+1,Thread.NORM_PRIORITY);
        myThread thread2=new myThread(slider,-1,Thread.NORM_PRIORITY);

        JButton startButton= new JButton("Start");
        startButton.addActionListener(e->{
            thread1.start();
            thread2.start();
            startButton.setEnabled(false);
        });
        JPanel first_lab_panel= new JPanel();
        first_lab_panel.setLayout(new BoxLayout(first_lab_panel, BoxLayout.Y_AXIS));

        first_lab_panel.add(thread1.GetJPanel());
        first_lab_panel.add(thread2.GetJPanel());
        first_lab_panel.add(slider);

        JPanel jPanel= new JPanel();
        jPanel.add(startButton);
        first_lab_panel.add(jPanel);

        first_lab.setContentPane(first_lab_panel);
        first_lab.setVisible(true);
    }
}
