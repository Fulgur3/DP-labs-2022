package first.b;

import javax.swing.*;

class myThread extends Thread{
    int value;
    public myThread(int value){
        this.value=value;
    }

    @Override
    public void run(){
    while(b.Semaphore!=0);
    b.Semaphore=1;
    b.label1.setText("Busy space");
    while(!interrupted()){
        int temp=b.slider.getValue();
        if((temp>10&& value<0 ) || (temp <90 && value>0)){
            b.slider.setValue((temp+value));
        }
        b.Semaphore=0;
        b.label1.setText("available space");
    }
    }

}


public class b {
    static volatile int Semaphore=0;
    static volatile JSlider slider;
    static volatile JLabel label1;
    static private myThread thread1;
    static private myThread thread2;
    static private JButton thread1StartButton;
    static private JButton thread2StartButton;
    static private JButton thread1StopButton;
    static private JButton thread2StopButton;
    public static void main(String[] args){
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        JPanel panel= new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        slider= new JSlider(0,100);
        panel.add(slider);
        label1=new JLabel("available");

        JPanel Thread1Panel =  new JPanel();
        thread1StartButton= new JButton("start first");
        thread1StopButton= new JButton("stop first");
        thread1StopButton.setEnabled(false);
        thread1StartButton.addActionListener(e->{
                thread1= new myThread(+1);
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread1StopButton.setEnabled(true);
        thread1StartButton.setEnabled(false);
        thread1.start();
        });
        thread1StopButton.addActionListener(e->{
            thread1.interrupt();
            thread1StopButton.setEnabled(false);
            thread1StartButton.setEnabled(true);
        });
        Thread1Panel.add(thread1StartButton);
        Thread1Panel.add(thread1StopButton);


        JPanel Thread2Panel =  new JPanel();
        thread2StartButton= new JButton("start second");
        thread2StopButton= new JButton("stop second");
        thread2StopButton.setEnabled(false);
        thread2StartButton.addActionListener(e->{
            thread2= new myThread(-1);
            thread2.setPriority(Thread.MAX_PRIORITY);
            thread2StopButton.setEnabled(true);
            thread2StartButton.setEnabled(false);
            thread2.start();
        });
        thread2StopButton.addActionListener(e->{
            thread2.interrupt();
            thread2StopButton.setEnabled(false);
            thread2StartButton.setEnabled(true);
        });
        Thread2Panel.add(thread2StartButton);
        Thread2Panel.add(thread2StopButton);

        panel.add(Thread1Panel);
        panel.add(Thread2Panel);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
