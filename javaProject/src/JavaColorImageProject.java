import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.awt.image.*;

public class JavaColorImageProject extends JFrame implements ActionListener{
	//ȭ����ó���� ���� ��ϸ���Ʈ(���ڿ� �迭)
	private String[] algo1 = {"ȭ����ó��","���Ͽ���","��������","������ϱ�/����", "������ϱ�","���󳪴���",
				"��� 127����","�����ձ���","�Ķ󺼶���","�Ķ󺼶�ĸ","����","�׷��̽�����"};
	//������ó���� ���� ��ϸ���Ʈ(���ڿ� �迭)
	private String[] algo2 = {"������ó��","���Ϲ̷���","�¿�̷���","�����̵�"};
	//ȭ�ҿ���ó���� ���� ��ϸ���Ʈ(���ڿ� �迭)
	private String[] algo3 = {"ȭ�ҿ���ó��","������","����","������","����þ�","�����Ļ�����","�̵�������","���翬����",
			"�ι����˰���","�Һ��˰���","�������˰���","���ö�þȾ˰���","�α׾˰���","���׾˰���"};
	//������׷�ó���� ���� ��ϸ���Ʈ(���ڿ� �迭)
	private String[] algo4 = {"������׷�ó��","��Ʈ��Ī","����-��","��Ȱȭ"};
	//������� RGB->HSV�𵨷� �ٲ� ó���� ���� ��ϸ���Ʈ(���ڿ� �迭)
	private String[] algo5 = {"HSV��ó��","ä����ȯ","����ȯ","�������̹�������"};
	
	//ȭ����ó���� ���� ��ϸ���Ʈ(algo1)�� �гο� �߰��� JComboBox(algo11)�� �����.
	private JComboBox<String> algo11 = new JComboBox<String>(algo1);
	//������ó���� ���� ��ϸ���Ʈ(algo2)�� �гο� �߰��� JComboBox(algo22)�� �����.
	private JComboBox<String> algo22 = new JComboBox<String>(algo2);
	//ȭ�ҿ���ó���� ���� ��ϸ���Ʈ(algo3)�� �гο� �߰��� JComboBox(algo33)�� �����.
	private JComboBox<String> algo33 = new JComboBox<String>(algo3);
	//������׷�ó���� ���� ��ϸ���Ʈ(algo4)�� �гο� �߰��� JComboBox(algo44)�� �����.
	private JComboBox<String> algo44 = new JComboBox<String>(algo4);
	//HSV��ó���� ���� ��ϸ���Ʈ(algo5)�� �гο� �߰��� JComboBox(algo55)�� �����.
	private JComboBox<String> algo55 = new JComboBox<String>(algo5);
	
	//���� �Է� ���� ���� ��Ī..�ȳ���
	private JLabel la1 = new JLabel("para1:");
	private JLabel la2 = new JLabel("para2:");
	
	//����ó���� ���� �޾ƿ;��ϴ� ���� �Է��� JTextField
	private JTextField tf1 = new JTextField(10);
	//�����̵�ó�� �ÿ��� ���� 2�� �ʿ��ϹǷ�
	private JTextField tf2 = new JTextField(10);
	
	//p1���� JComboBox���� ����
	private JPanel p1 = new JPanel();
	//p2���� JLabel�� JTextField�� ����.
	private JPanel p2 = new JPanel();
	
	//������ �ҷ����� �����ϱ⸦ �� ��ư �����
	//�޴��� ���� ��(Ʋ)����
	private JMenuBar mb = new JMenuBar();
	//File�̶�� �̸��� �޴���ư����
	private JMenu file = new JMenu("File");
	//File�޴���ư �ȿ� �� JMenuItem����
	//OPEN�� ���� ���Ⱑ �ɰ��̰� 
	//SAVE�� ���� �����ϱⰡ �� ���̴�.
	private JMenuItem fopen = new JMenuItem("OPEN");
	private JMenuItem fsave = new JMenuItem("SAVE");
	
	//���ϴ��̾�α�!!������ �����ϰ� ���⸦ ������ ���� Ž��â�� ����.
	//FileDialog���� ����� â�� �̸��κп� �ߴ� �޽����̴�. FileDialog.LOAD�� ������ ������ FileDialog�� �����̴�.
	private FileDialog fdlg1 = new FileDialog(this,"����",FileDialog.LOAD);
	//FileDialog.SAVE�� �翬�� ������ ������ ����
	private FileDialog fdlg2 = new FileDialog(this,"����",FileDialog.SAVE);
	
	// pm�� �׸��� �� �г�
	private MyPanel pm = new MyPanel();
	
	
	public JavaColorImageProject() {
		setTitle("JavaColorImageProject");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
		start();
		setSize(800,700);
		setVisible(true);
	}
	public void init() {
		setContentPane(pm);
		setLayout(new FlowLayout());
		p1.setLayout(new FlowLayout());
		p1.add(algo11);p1.add(algo22);p1.add(algo33);p1.add(algo44);p1.add(algo55);
		p2.add(la1);p2.add(tf1);p2.add(la2);p2.add(tf2);
		file.add(fopen);file.add(fsave);
		mb.add(file);
		setJMenuBar(mb);
		
		add(p1);add(p2);
	}
	public void start() {
		fopen.addActionListener(this);
		fsave.addActionListener(this);
		algo11.addActionListener(this);
		algo22.addActionListener(this);
		algo33.addActionListener(this);
		algo44.addActionListener(this);
		algo55.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//�� �ڵ忡���� ��� �̺�Ʈ�� ActionListener�� ���ư��Ƿ� 
		//�Լ� �ȿ��� � �������� �̺�Ʈ�� �߻��ߴ����� �˾Ƴ��� if���� �ʿ��ϴ�.
		if(e.getSource()==fopen) {
			fdlg1.setVisible(true);
			inputImage();
		}
		else if(e.getSource()==fsave) {
			fdlg2.setVisible(true);
			outputImage();
		}
		else if(e.getSource()==algo11) {
			//JComboBox���� � �κ��� Ŭ���ߴ��� ������ ������������
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
			//���õ� �������� �ε����� �����´�.
			int index = cb.getSelectedIndex();
			
			//���ڿ��� ó���ϸ� ���� ���� �� ���� �̾���ϱ⶧���� 10�̶�� ���ڿ� �ε����� ���̸� �ȴٴ� �ļ���
			//���� 10�� �Ѿ�� �Ǹ� 1011���� 1000�� �Ѿ ���ڷ� ���ϱ� ������ ��츦 ����� �Լ��� ȣ�����־���.
			if(index>=10) {
				//algoFunction�� ���� switch������ ó���� �Լ��� �Űܰ���.
				algoFunction(""+"1"+index);
			}else {
				algoFunction(""+"10"+index);
			}
		}
		else if(e.getSource()==algo22) {
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
			int index = cb.getSelectedIndex();
			algoFunction(""+"20"+index);
		}
		else if(e.getSource()==algo33) {
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
			int index = cb.getSelectedIndex();
			if(index>=10) {
				algoFunction(""+"3"+index);
			}else {
				algoFunction(""+"30"+index);
			}
		}
		else if(e.getSource()==algo44) {
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
			int index = cb.getSelectedIndex();
			algoFunction(""+"40"+index);
		}
		else if(e.getSource()==algo55) {
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
			int index = cb.getSelectedIndex();
			algoFunction(""+"50"+index);
		}
	}
	public void algoFunction(String index) {
		//index�� ������ �Լ��� ã�ư���.
		//java�� switch������ ���ڿ��� ���� �� �ִ�.
		switch(index) {
		case "101"://���Ͽ���
			equalImage();break;
		case "102"://��������
			reverseImage();break;
		case "103"://���� ���ϱ�/����
			addImage();break;
		case "104"://���� ���ϱ�
			gopImage();break;
		case "105"://���� ������
			divImage();break;
		case "106"://���127����
			image127();break;
		case "107"://��� ��� ����
			avgImage();break;
		case "108"://�Ķ󺼶� ��
			paraCupImage();break;
		case "109"://�Ķ󺼶� ĸ
			paraCapImage();break;
		case "110"://����
			gammaImage();break;
		case "111"://�׷��̽�����
			grayImage();break;
			
			
		case "201"://���Ϲ̷���
			udImage();break;
		case "202"://�¿�̷���
			lrImage();break;
		case "203"://�����̵�
			swapImage();break;
			
			
		case "301"://������
			embossingImage();break;
		case "302"://����
			blurrImage();break;
		case "303"://������
			sharpenImage();break;
		case "304"://����þ�
			gaussianImage();break;
		case "305"://������ ������
			hpfSharpImage();break;
		case "306"://�̵��� ����
			sadImage();break;
		case "307"://���翬����
			opImage();break;
		case "308"://�ι��� �˰���
			robertsImage();break;
		case "309"://�Һ� �˰���
			sobelImage();break;
		case "310"://������ �˰���
			prewittImage();break;
		case "311"://���ö�þ� �˰���
			laplacianImage();break;
		case "312"://�α� �˰���
			logImage();break;
		case "313"://���� �˰���
			dogImage();break;

			
		case "401"://��Ʈ��Ī
			strechImage();break;
		case "402"://����-��
			endInImage();break;
		case "403"://��Ȱȭ
			equalizeImage();break;
			
			
		case "501"://ä����ȯ
			saturImage();break;
		case "502"://����ȯ
			intensityImage();break;
		case "503"://�������̹�������
			orangeImage();break;
		}
	}
	
	///////////////////////////////////////////////////
	////���������////////////////////////////////////////
	///////////////
	//����������
	int[][][] inImage;
	int inH,inW;
	int[][][] outImage;
	static int outH,outW;
	File inFp,outFp;
	float[] rgb = new float[3];
	float[] hsv = new float[3];
	float[] abc = new float[3];
	float[] def = new float[3];
	//Parameter Variable
	String algo, para1, para2;
	String inFname, outFname;
	BufferedImage bImage;
	
	////////////////
	///�����ڵ��
	public void inputImage() {//������ �̹����� �Է¹迭�� �Է� �޾� ����ϴ� �Լ�
		try {
		//FileDialoge���� ������ �ϴ� ������ ��θ� ������ FileŬ������ �����ϴ��ڵ��̴�.
		//getDirectory()�� ������ ���͸� ��θ� �����´�.
		//getFile()�� ������ ���ϸ��� �����´�.
		inFp =new File(fdlg1.getDirectory()+fdlg1.getFile());
		//FileŬ������ ������ ��ο� ���� ������ BufferedImageŬ������ �����Ѵ�.
		bImage = ImageIO.read(inFp);
		
		//BufferedImage�� ����� �׸��� ���� ���ΰ� �ݴ�� �Ǿ��ִ�.
		//�̷��� �ݴ�� ���������� �迭 ���� �ʰ� ������ ����.
		inH = bImage.getWidth();
		inW = bImage.getHeight();
		
		inImage = new int[3][inH][inW];
		
		for(int i=0;i<inH;i++) {
			for(int k=0;k<inW;k++) {
				//BufferedImage�� ����� �̹����� �� �ȼ��� RGB���� �����´�.
				//�� ���� 0xf0ed77�̷������� �����Ǿ��ִ�.
				int rgb=bImage.getRGB(i,k);
				
				//RGB���� R���� G���� B������ ������ ������ ���� �Է� �޴´�.
				int r =(rgb>>16)&0xFF;
				int g =(rgb>>8)&0xFF;
				int b=(rgb>>0)&0xFF;
				
				//RGB���� �Է� �迭�� �Է�
				inImage[0][i][k]=r;
				inImage[1][i][k]=g;
				inImage[2][i][k]=b;
				
				//���� ����� ���� �ٽ� ���ۿ� �Է��Ѵ�.
				//�� ���ŷο� ������ ��ġ�� ������ drawImage�� �迭�� �޾� �׸��� ���ϰ�
				//���ۿ� �Էµ� �̹����� �׸� �� �ֱ� �����̴�.
				bImage.setRGB(i, k, rgb);
			}
		}
		//repaint()�� ���� ���ۿ� �Էµ� �̹����� ����Ѵ�.
		repaint();
		
		}catch(Exception e){
			System.out.println("BBBBB255��"+e);
		}
		
		
	}
	class MyPanel extends JPanel{//�г��� ��ӹ��� Ŭ������ ����� paintComponent()�Լ��� �߰��Ѵ�.
		//repaint()�ϸ� �� �Լ��� ȣ��Ǵ� ���̴�.
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//���ۿ� �ִ� �̹����� �����
			g.drawImage(bImage,50,100, this);
		}
	}
	public void displayImage() {//����ó���� �̹����� ����ϴ� �Լ� 
		try {
			
			for(int i=0;i<outH;i++) {
				for(int k=0;k<outW;k++) {
					//outImage�� ����ִ� �ȼ����� �� R,G,B�� ������ �ִ´�.
					int r = outImage[0][i][k];
					int g = outImage[1][i][k];
					int b = outImage[2][i][k];
					//RGB�ȼ������� ���ļ� �ϳ��� ���� ���� ������ ����
					int px=0;
					px=px|(r<<16);
					px=px|(g<<8);
					px=px|(b<<0);
					//�ٽ� 0xff08e6���� ���� ���ϰ� �̹����� ������ ���ۿ� �ִ´�.
					bImage.setRGB(i, k, px);
				}
			}

			//�ٽ� �׸�
			repaint();
			
			}catch(Exception e){
				System.out.println("kkkkkkk321��"+e);
			}
	}
	

	public void outputImage() {//����ó���� �̹����� ���ϴ� ��ġ�� �����ϴ� �Լ�
		try {
			//�̹����� ��ġ�� �����Ѵ�. �׳� �����ϸ�  Ȯ���ھ��� ���Ϸ� ������ ������ ������� Ȯ���ڸ� ���Ƿ� jpg�� �����ش�.
			outFp=new File(fdlg2.getDirectory()+fdlg2.getFile()+".jpg");
			//RGB���� �Է¹��� ���۶�� �ǹ�(BufferedImage.TYPE_INT_RGB)
			BufferedImage oImage = new BufferedImage(outH,outW,BufferedImage.TYPE_INT_RGB);
			for(int i=0;i<outH;i++) {
				for(int k=0;k<outW;k++) {
					//�����ϰ� ���� �̹����� �迭�� rgb�� �����Ѵ�.
					int r = outImage[0][i][k];
					int g = outImage[1][i][k];
					int b = outImage[2][i][k];
					//�ȼ��� �ϳ��� ��ģ��.
					int px=0;
					px=px|(r<<16);
					px=px|(g<<8);
					px=px|(b<<0);
					//����� ���ۿ� �ȼ��� �ϳ��� �ִ´�.
					oImage.setRGB(i, k, px);
				}
			}
			//���ۿ� ��� �̹����� jpg�������� ������ ���� ��ġ�� �����Ѵ�.
			ImageIO.write(oImage,"jpg",outFp);
		}catch(Exception e) {
			System.out.println("CCCCCC245"+e);
		}
	}
	
	////////////////////////////////////////////////
	////ȭ����ó��/////////////////////////////////////
	public void equalImage() {//���Ͽ��� �˰���
		outH=inH;
		outW=inW;
		outImage= new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					outImage[rgb][i][k]=inImage[rgb][i][k];
				}
			}
		}
		displayImage();
	}
	public void reverseImage() {//�������� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		//**Image Processing Algorithm**
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					outImage[rgb][i][k]= 255-inImage[rgb][i][k];
				}
			}
		}
		displayImage();
	}
	public void addImage() {//���� ���/��Ӱ� �˰���
		outH=inH;
		outW=inW;
		outImage=new int[3][outH][outW];
		//**Image Processing Algorithm **
		int value = Integer.parseInt(tf1.getText());
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					if(inImage[rgb][i][k]+value>255)
						outImage[rgb][i][k]=255;
					else if(inImage[rgb][i][k]+value<0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=inImage[rgb][i][k]+value;
				}
			}
		}
		displayImage();
	}
	public void gopImage() {//���� ���ϱ� �˰���
		outH=inH;
		outW=inW;
		outImage=new int[3][outH][outW];
		//**Image Processing Algorithm **
		int value = Integer.parseInt(tf1.getText());
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					if(inImage[rgb][i][k]*value>255)
						outImage[rgb][i][k]=255;
					else if(inImage[rgb][i][k]*value<0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=inImage[rgb][i][k]*value;
				}
			}
		}
		displayImage();
	}
	public void divImage() {//���� ������ �˰���
		outH=inH;
		outW=inW;
		outImage=new int[3][outH][outW];
		//**Image Processing Algorithm **
		int value = Integer.parseInt(tf1.getText());
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					if(inImage[rgb][i][k]/value>255)
						outImage[rgb][i][k]=255;
					else if(inImage[rgb][i][k]/value<0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=inImage[rgb][i][k]/value;
				}
			}
		}
		displayImage();
	}
	public void image127() {//��� 127���غ�ȯ �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		//** Image Processing Algorithm **
		for(int i=0;i<inH;i++){
			for(int k=0;k<inW;k++){
				int sumValue = inImage[0][i][k]+inImage[1][i][k]+inImage[2][i][k];
				int avgValue = sumValue/3;
				
				if(avgValue>127){
					outImage[0][i][k]=255;
					outImage[1][i][k]=255;
					outImage[2][i][k]=255;
				}
				else{
					outImage[0][i][k]=0;
					outImage[1][i][k]=0;
					outImage[2][i][k]=0;
				}
			}
		}
		displayImage();
	}
	public void avgImage() {//��� ��ձ��� ��ȯ �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		int hap=0,avg,cnt=0;
		
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					hap+=inImage[rgb][i][k];
					cnt++;
				}
			}
		}
		
		avg = hap/cnt;
		//** Image Processing Algorithm **
		for(int i=0;i<inH;i++){
			for(int k=0;k<inW;k++){
				int sumValue = inImage[0][i][k]+inImage[1][i][k]+inImage[2][i][k];
				int avgValue = sumValue/3;
				
				if(avgValue>avg){
					outImage[0][i][k]=255;
					outImage[1][i][k]=255;
					outImage[2][i][k]=255;
				}
					
				else{
					outImage[0][i][k]=0;
					outImage[1][i][k]=0;
					outImage[2][i][k]=0;
				}
			}
		}
		displayImage();
	}
	public void paraCupImage() {//�Ķ󺼶� �� �˰���
		outH=inH;
		outW=inW;
		outImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++){
				for(int k=0;k<outW;k++){
					outImage[rgb][i][k]=(int)(Math.pow((double)(inImage[rgb][i][k]/128-1),(double)2)*255);
				}
			}
		}
		displayImage();
	}
	public void paraCapImage() {//�Ķ󺼶� ĸ �˰���
		outH=inH;
		outW=inW;
		outImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++){
				for(int k=0;k<outW;k++){
					outImage[rgb][i][k]=(int)(255-(255)*Math.pow((double)(inImage[rgb][i][k]/128-1),(double)2));
				}
			}
		}
		displayImage();
	}
	public void gammaImage() {//���� �˰���
		outH =inH;
		outW =inW;
		// �޸� �Ҵ�
		outImage = new int[3][outH][outW];
		/// ** Image Processing Algorithm **
		double value = Double.parseDouble(tf1.getText());
		if(value <0)
			value =1/(1-value);
		else
			value +=1;
		//���� ��ȯ
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0; i<inH; i++){
				for(int k=0; k<inW; k++){
					double result=(Math.pow((double)(inImage[rgb][i][k]/255.0),(double)(value))*255+0.5);
					if(result <0)
						result=0;
					else if(result >255)
						result=255;
					outImage[rgb][i][k] = (int)result;
				}
			}
		}
		displayImage();
	}
	/////////////////////////////////////////////
	/////������ó��////////////////////////////////
	public void udImage() {//�¿� ���� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		//** Image Processing Algorithm **
		for(int i=0;i<inH;i++){
			for(int k=0;k<inW;k++){
				outImage[0][i][k]=inImage[0][i][inW-k-1];
				outImage[1][i][k]=inImage[1][i][inW-k-1];
				outImage[2][i][k]=inImage[2][i][inW-k-1];
			}
		}
		displayImage();
	}
	public void lrImage() {//���� ���� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		//** Image Processing Algorithm **
		for(int i=0;i<inH;i++){
			for(int k=0;k<inW;k++){
				outImage[0][i][k]=inImage[0][inH-i-1][k];
				outImage[1][i][k]=inImage[1][inH-i-1][k];
				outImage[2][i][k]=inImage[2][inH-i-1][k];
			}
		}
		displayImage();
	}
	public void swapImage() {//�����̵� �˰���
		int x = Integer.parseInt(tf1.getText());
		int y = Integer.parseInt(tf2.getText());
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					if((i+x)<outH && (k+y)<outW)
						outImage[rgb][i+x][k+y]=inImage[rgb][i][k];
					else break;
				}
			}
		}
		displayImage();
	}
	
	///////////////////////////////////////////////
	////ȭ�ҿ���ó��//////////////////////////////////
	public void embossingImage() {//������ �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{-1.0,0.0,0.0},
				{0.0,0.0,0.0},
				{0.0,0.0,1.0}
				};
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		//sum=1
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
			}
		displayImage();
	}
	public void blurrImage() {//���� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{1.0/25,1.0/25,1.0/25,1.0/25,1.0/25},
				{1.0/25,1.0/25,1.0/25,1.0/25,1.0/25},
				{1.0/25,1.0/25,1.0/25,1.0/25,1.0/25},
				{1.0/25,1.0/25,1.0/25,1.0/25,1.0/25},
				{1.0/25,1.0/25,1.0/25,1.0/25,1.0/25}
				};
		double [][][]tmpInImage = new double[3][outH+4][outW+4];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+4;i++){
				for(int k=0;k<inW+4;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+2][k+2]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<5;m++)
						for(int n=0;n<5;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		 //sum=1
//		for(int rgb=0;rgb<3;rgb++)
//		 for(int i=0;i<outH;i++)
//		 	for(int k=0;k<outW;k++)
//		 		tmpOutImage[rgb][i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
		for(int i=0;i<outH;i++)
			for(int k=0;k<outW;k++){
				if(tmpOutImage[rgb][i][k]>255.0)
					outImage[rgb][i][k]=255;
				else if(tmpOutImage[rgb][i][k]<0.0)
					outImage[rgb][i][k]=0;
				else
					outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
			}
		}
		displayImage();
	}
	public void sharpenImage() {//������ �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{-1.0,-1.0,-1.0},
				{-1.0,9,-1.0},
				{-1.0,-1.0,-1.0}
				};
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=1
		// for(int i=0;i<outH;i++)
//		 	for(int k=0;k<outW;k++)
//		 		tmpOutImage[i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void gaussianImage() {//����þ� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{1.0/16, 1.0/8, 1.0/16},
				{1.0/8, 1.0/4, 1.0/8},
				{1.0/16, 1.0/8, 1.0/16}
				};
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=1
		// for(int i=0;i<outH;i++)
//		 	for(int k=0;k<outW;k++)
//		 		tmpOutImage[i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void hpfSharpImage() {//�����Ļ����� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{-1.0/9, -1.0/9, -1.0/9},
				{-1.0/9, 8.0/9, -1.0/9},
				{-1.0/9, -1.0/9, -1.0/9}
				};
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=100.0*tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=0
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void sadImage() {//�̵��� ���� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{0.0, -1.0, 0.0},
				{-1.0, 2.0, 0.0},
				{0.0, 0.0, 0.0}
				};
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=0
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
			
			for(int rgb=0;rgb<3;rgb++){
				for(int i=0;i<outH;i++)
					for(int k=0;k<outW;k++){
						if(tmpOutImage[rgb][i][k]>255.0)
							outImage[rgb][i][k]=255;
						else if(tmpOutImage[rgb][i][k]<0.0)
							outImage[rgb][i][k]=0;
						else
							outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
					}
			}
		displayImage();
	}
	public void opImage() {//���翬���� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double max = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							if(Math.abs(tmpInImage[rgb][i+1][k+1]-tmpInImage[rgb][i+m][k+n])>=max)
								max = Math.abs(tmpInImage[rgb][i+1][k+1]-tmpInImage[rgb][i+m][k+n]);
					tmpOutImage[rgb][i][k]=(int)max;
				}
			}
		}
		// // //sum=0
		// for(int i=0;i<outH;i++)
//		 	for(int k=0;k<outW;k++)
//		 		tmpOutImage[i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void robertsImage() {//�ι��� ����ũ �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{-1.0, 0.0, -1.0},
				{0.0, 2.0, 0.0},
				{0.0, 0.0, 0.0}
				};
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=0
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void sobelImage() {//�Һ� ����ũ �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{0.0, -2.0, -2.0},
				{2.0, 0.0, -2.0},
				{2.0, 2.0, 0.0}
				};
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=0
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void prewittImage() {//������ ����ũ �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		
		double [][]mask = {
				{0.0, -1.0, -2.0},
				{1.0, 0.0, -1.0},
				{2.0, 1.0, 0.0}
				};
		
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		
		int [][][] tmpOutImage = new int[3][outH][outW];
		
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		
		
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=0
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
		
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void laplacianImage() {//���ö�þ� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
				{0.0, 1.0, 0.0},
				{1.0, -4.0, 1.0},
				{0.0, 1.0, 0.0}
				};
		double [][][]tmpInImage = new double[3][outH+2][outW+2];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+2;i++){
				for(int k=0;k<inW+2;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+1][k+1]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<3;m++)
						for(int n=0;n<3;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=0
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void logImage() {//�α� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		double [][]mask = {
		            {0.0,0.0,-1.0,0.0,0.0},
		            {0.0,-1.0,-2.0,-1.0,0.0},
		            {-1.0,-2.0,16.0,-2.0,-1.0},
		            {0.0,-1.0,-2.0,-1.0,0.0},
		            {0.0,0.0,-1.0,0.0,0.0}
		        };
		double [][][]tmpInImage = new double[3][outH+4][outW+4];
		int [][][] tmpOutImage = new int[3][outH][outW];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+4;i++){
				for(int k=0;k<inW+4;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+2][k+2]=inImage[rgb][i][k];
				}
			}
		}
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<5;m++)
						for(int n=0;n<5;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=0
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	public void dogImage() {//���� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		
		double[][] mask = {
		            {0.0,0.0,0.0,-1.0,-1.0,-1.0,0.0,0.0,0.0},
		            {0.0,-2.0,-3.0,-3.0,-3.0,-3.0,-3.0,-2.0,0.0},
		            {0.0,-3.0,-2.0,-1.0,-1.0,-1.0,-2.0,-3.0,0.0},
		            {-1.0,-3.0,-1.0,9.0,9.0,9.0,-1.0,-3.0,-1.0},
		            {-1.0,-3.0,-1.0,9.0,19.0,9.0,-1.0,-3.0,-1.0},
		            {-1.0,-3.0,-1.0,9.0,9.0,9.0,-1.0,-3.0,-1.0},
		            {0.0,-3.0,-2.0,-1.0,-1.0,-1.0,-2.0,-3.0,0.0},
		            {0.0,-2.0,-3.0,-3.0,-3.0,-3.0,-3.0,-2.0,0.0},
		            {0.0,0.0,0.0,-1.0,-1.0,-1.0,0.0,0.0,0.0}
		            
		};
		
		double [][][]tmpInImage = new double[3][outH+8][outW+8];
		
		int [][][] tmpOutImage = new int[3][outH][outW];
		
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH+8;i++){
				for(int k=0;k<inW+8;k++){
					tmpInImage[rgb][i][k]=127.0;
				}
			}
		}
		
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					tmpInImage[rgb][i+4][k+4]=inImage[rgb][i][k];
				}
			}
		}
		
		
		//** Image Processing Algorithm **
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double S = 0.0;
					for(int m=0;m<9;m++)
						for(int n=0;n<9;n++)
							S+=tmpInImage[rgb][i+m][k+n]*mask[m][n];
					tmpOutImage[rgb][i][k]=(int)S;
				}
			}
		}
		// //sum=0
		for(int rgb=0;rgb<3;rgb++)
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++)
					tmpOutImage[rgb][i][k]+=127.0;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<outH;i++)
				for(int k=0;k<outW;k++){
					if(tmpOutImage[rgb][i][k]>255.0)
						outImage[rgb][i][k]=255;
					else if(tmpOutImage[rgb][i][k]<0.0)
						outImage[rgb][i][k]=0;
					else
						outImage[rgb][i][k]=(int)tmpOutImage[rgb][i][k];
				}
		}
		displayImage();
	}
	
	/////////////////////////////////////////////
	////������׷�ó��//////////////////////////////
	public void strechImage() {//��Ʈ��Ī �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		float tmpHSVImage[][][]=new float[3][outH][outW];
		
		
		//HSV��ȯ->tmpHSVImage�� ����
		for(int i=0;i<inH;i++) {
			for(int k=0;k<inW;k++) {
				float R = inImage[0][i][k];
				float G = inImage[1][i][k];
				float B = inImage[2][i][k];
				
				abc=rgb2hsv(R,G,B);
				
				tmpHSVImage[0][i][k]=abc[0]*360;//H
				tmpHSVImage[1][i][k]=abc[1]*100;//S
				tmpHSVImage[2][i][k]=abc[2]*100;//V
			}
		}
		//���� ���� ��Ʈ��Ī
		float LOW=tmpHSVImage[2][0][0],HIGH=tmpHSVImage[2][0][0];
		
		for(int i=0;i<inH;i++) {
			for(int k=0;k<inW;k++) {
				if(LOW>tmpHSVImage[2][i][k])
					LOW=tmpHSVImage[2][i][k];
				if(HIGH<tmpHSVImage[2][i][k])
					HIGH=tmpHSVImage[2][i][k];
			}
		}
		for(int i=0;i<inH;i++) {
			for(int k=0;k<inW;k++) {
				double out = (double)((tmpHSVImage[2][i][k]-LOW)*255/(HIGH-LOW));
				if(out<0.0)
					out=0;
				else if(out>255.0)
					out=255;
				else
					out=out;
				tmpHSVImage[2][i][k]=(int)out;
			}
		}
		
		for(int i=0;i<inH;i++) {
			for(int k=0;k<inW;k++) {
				float H = tmpHSVImage[0][i][k]/360;
				float S = tmpHSVImage[1][i][k]/100;
				float V = tmpHSVImage[2][i][k]/100;
				
				def=hsv2rgb(H,S,V);
				
				int R = (int)def[0];
				int G = (int)def[1];
				int B = (int)def[2];
				
				outImage[0][i][k]=R;
				outImage[1][i][k]=G;
				outImage[2][i][k]=B;
			}
		}

		displayImage();
	}
	public void endInImage() {//����-�� �˰���
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		//** Image Processing Algorithm **
		int LOW = inImage[0][0][0],HIGH=inImage[0][0][0];
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++)
				for(int k=0;k<inW;k++){
					if(LOW>inImage[rgb][i][k])
						LOW = inImage[rgb][i][k];
					if(HIGH<inImage[rgb][i][k])
						HIGH=inImage[rgb][i][k];
				}
		}
		LOW+=50;
		HIGH+=50;
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					double outa = (double)((inImage[rgb][i][k]-LOW)*255/(HIGH-LOW));
					if(outa<0.0)
						outa=0;
					else if(outa>255.0)
						outa=255;
					else
						outa=(int)outa;
					outImage[rgb][i][k] = (int)outa;
				}
			}
		}
		displayImage();
	}
	public void equalizeImage() {//��Ȱȭ �˰���
		outH=inH;
		outW=inW;
		outImage = new int[3][outH][outW];
		
		int histoR[] = new int[256];
		int histoG[] = new int[256];
		int histoB[] = new int[256];
		
		for(int i=0;i<256;i++){
			histoR[i]=0;
			histoG[i]=0;	
			histoB[i]=0;
		}
			
		for(int i=0;i<inH;i++)
			for(int k=0;k<inW;k++){
				histoR[inImage[0][i][k]]++;
				histoG[inImage[1][i][k]]++;
				histoB[inImage[2][i][k]]++;
			}
		int sumHistoR[] = new int[256];
		int sumHistoG[] = new int[256];
		int sumHistoB[] = new int[256];
		
		for(int i=0;i<256;i++){
			sumHistoR[i]=0;
			sumHistoG[i]=0;
			sumHistoG[i]=0;
		}
		int sumValueR=0;
		int sumValueG=0;
		int sumValueB=0;
		
		for(int i=0;i<256;i++){
			sumValueR += histoR[i];
			sumHistoR[i]=sumValueR;
			
			sumValueG += histoG[i];
			sumHistoG[i]=sumValueG;
			
			sumValueB += histoB[i];
			sumHistoB[i]=sumValueB;
		}
		double normalHistoR[] = new double[256];
		double normalHistoG[] = new double[256];
		double normalHistoB[] = new double[256];
		
		for(int i=0;i<256;i++){
			normalHistoR[i]=0.0;
			normalHistoG[i]=0.0;
			normalHistoB[i]=0.0;
		}
		for(int i=0;i<256;i++){
			double normalR = sumHistoR[i]*(1.0/(inH*inW))*255.0;
			normalHistoR[i] = normalR;
			double normalG = sumHistoG[i]*(1.0/(inH*inW))*255.0;
			normalHistoG[i] = normalG;
			double normalB = sumHistoB[i]*(1.0/(inH*inW))*255.0;
			normalHistoB[i] = normalB;
		}
		for(int rgb=0;rgb<3;rgb++){
			for(int i=0;i<inH;i++){
				for(int k=0;k<inW;k++){
					outImage[0][i][k]=(int)normalHistoR[inImage[0][i][k]];
					outImage[1][i][k]=(int)normalHistoG[inImage[1][i][k]];
					outImage[2][i][k]=(int)normalHistoB[inImage[2][i][k]];
				}
			}
	}
		displayImage();
	}
	////////////////////////////////////////////
	////HSV��ȯ �Լ�///////////////////////////////
	public float[] rgb2hsv(float r, float g, float b) {//rgb->hsv��ȯ
		
		float max1 = Math.max(r,g);
		float max2 = Math.max(g,b);
		float max = Math.max(max1,max2);
		
		float min1 = Math.min(r,g);
		float min2 = Math.min(g,b);
		float min = Math.min(min1, min2);
		
		float d = max - min; //Delta RGB value
	 
		float h=0, s;
		float v = max / 255;
		
		if (max==0)
			 s = 0;
		else
			 s = d/max;
		   
		
		if(max==min){
			h = 0;
			}
		else if(max==r){
			h = (g - b) + d * (g < b ? 6: 0); h /= 6 * d;
		}
		else if(max==g){
			h = (b - r) + d * 2; h /= 6 * d;
		}
		else{
			h = (r - g) + d * 4; h /= 6 * d;
		}
		
									 
		hsv[0] = (float)(h);
		hsv[1] = (float)(s);
		hsv[2] = (float)(v);
		
		return hsv;
		}
	public float[] hsv2rgb(float h, float s, float v){//hsv->rgb��ȯ
		
		float r=0, g=0, b=0, f, p, q, t;
		 
		 h=h*360; s=s*100; v=v*100;
		 
	     h = Math.max(0, Math.min(360, h));
	     s = Math.max(0, Math.min(100, s));
	     v = Math.max(0, Math.min(100, v));
	        
	     	
	     h /= 360;   s /= 100;     v /= 100;
	     int i = (int) Math.floor(h * 6);
	     f = h * 6 - i;
	     p = v * (1 - s);
	     q = v * (1 - f * s);
	     t = v * (1 - (1 - f) * s);
	          
	     if(i%6==0){
	    	 r = v; g = t; b = p;
	     }     
	     else if(i%6==1){
	    	 r = q; g = v; b = p;
	     }
	     else if(i%6==2){
	    	 r = p; g = v; b = t;
	     }
	     else if(i%6==3){
	    	 r = p; g = q; b = v;
	     }
	     else if(i%6==4){
	    	 r = t; g = p; b = v;
	     }
	     else if(i%6==5){
	    	 r = v; g = p; b = q;
	     }
	     
	    
	  	rgb[0] = (float) r*255;
	  	rgb[1] = (float) g*255;
	  	rgb[2] = (float) b*255;
		
	    return rgb;
	}
	////////////////////////////////////////////
	////HSV��ȯ ó��//////////////////////////////
	public void saturImage() {//ä�� ��ȯ �˰���
		// (�߿�!) ��� �̹����� ũ�Ⱑ ���� ---> �˰��� ����...
	    outH = inH;
	    outW = inW;
	    // ��� ������ 3���� �޸� �Ҵ�
	   	outImage = new int[3][inH][inW];
	    
	    // **** ��¥ ����ó�� �˰��� *****
		float s_value = Float.parseFloat(tf1.getText());
	 
	        for (int i=0; i<inH; i++) {
	            for (int k=0; k<inW; k++) {
	                float R = inImage[0][i][k];
	                float G = inImage[1][i][k];
	                float B = inImage[2][i][k];


	                // RGB --> HSV
	                //rgb2hsv(R,G,B);  // {h : 0~360, s : 0 ~ 1.0, v : 0 ~ 1.0}
	                abc = rgb2hsv(R,G,B);
	                
	                float H = abc[0];
	                float S = abc[1];
	                float V = abc[2];
	           	
	                
	                // ä���� ��������
	                S = S + s_value;
	                
	                // HSV --> RGB
	                
	                		
	                def = hsv2rgb(H,S,V);
					int R1 = (int) def[0]; 
					int G1 = (int) def[1];
					int B1 = (int) def[2];
	                // ��� ���� �ֱ�
	                outImage[0][i][k] = R1;
	                outImage[1][i][k] = G1;
	                outImage[2][i][k] = B1;
	            }
	        }            
	    // ******************************
	        displayImage();
	}
	public void grayImage() {//�׷��̽����� �˰���
		outH=inH;
		outW=inW;
		
		outImage = new int[3][outH][outW];
		
		for(int i=0;i<inH;i++){
			for(int k=0;k<inW;k++){
				int sumValue = inImage[0][i][k]+inImage[1][i][k]+inImage[2][i][k];
				int avgValue = sumValue/3;
				
				outImage[0][i][k]=avgValue;
				outImage[1][i][k]=avgValue;
				outImage[2][i][k]=avgValue;
			}
		}
		displayImage();
	}
	public void intensityImage() {//�� ���� �˰���
		// (�߿�!) ��� �̹����� ũ�Ⱑ ���� ---> �˰��� ����...
	    outH = inH;
	    outW = inW;
	    // ��� ������ 3���� �޸� �Ҵ�
	   	outImage = new int[3][inH][inW];
	    
	    // **** ��¥ ����ó�� �˰��� *****
		float v_value = Float.parseFloat(tf1.getText());
	 
	        for (int i=0; i<inH; i++) {
	            for (int k=0; k<inW; k++) {
	                float R = inImage[0][i][k];
	                float G = inImage[1][i][k];
	                float B = inImage[2][i][k];
	                
	                
	                // RGB --> HSV
	                //rgb2hsv(R,G,B);  // {h : 0~360, s : 0 ~ 1.0, v : 0 ~ 1.0}
	                abc = rgb2hsv(R,G,B);
	                
	                float H = abc[0];
	                float S = abc[1];
	                float V = abc[2];
	           	
	                
	                // ä���� ��������
	                V = V + v_value;
	                
	                // HSV --> RGB		
	                def = hsv2rgb(H,S,V);
					int R1 = (int) def[0]; 
					int G1 = (int) def[1];
					int B1 = (int) def[2];
					
	                // ��� ���� �ֱ�
	                outImage[0][i][k] = R1;
	                outImage[1][i][k] = G1;
	                outImage[2][i][k] = B1;
	            }
	        }            
	    // ******************************
	        displayImage();
	}
	public void orangeImage() {//������ ���� �˰���
		outH=inH;
		outW=inW;
		
		outImage= new int[3][outH][outW];
		
		for(int i=0;i<inH;i++){
			for(int k=0;k<inW;k++){
				int R = inImage[0][i][k];
				int G = inImage[1][i][k];
				int B = inImage[2][i][k];
				
				hsv = rgb2hsv(R,G,B);
				float H = hsv[0];
				float S = hsv[1];
				float v = hsv[2];
				
				if(8<=(H*360)&&(H*360)<=30){
					outImage[0][i][k]=R;
					outImage[1][i][k]=G;
					outImage[2][i][k]=B;
					
				}
				else{
					int avg =(R+G+B)/3;
					outImage[0][i][k]=avg;
					outImage[1][i][k]=avg;
					outImage[2][i][k]=avg;
					
				}
			}
		}
		displayImage();
	}

	public static void main(String[] ar) {
		new JavaColorImageProject();
	}
	
}