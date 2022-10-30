import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.awt.image.*;

public class JavaColorImageProject extends JFrame implements ActionListener{
	//화소점처리에 대한 목록리스트(문자열 배열)
	private String[] algo1 = {"화소점처리","동일영상","반전영상","영상더하기/빼기", "영상곱하기","영상나누기",
				"흑백 127기준","흑백평균기준","파라볼라컵","파라볼라캡","감마","그레이스케일"};
	//기하학처리에 대한 목록리스트(문자열 배열)
	private String[] algo2 = {"기하학처리","상하미러링","좌우미러링","영상이동"};
	//화소영역처리에 대한 목록리스트(문자열 배열)
	private String[] algo3 = {"화소영역처리","엠보싱","블러링","샤프닝","가우시안","고주파샤프닝","이동과차분","유사연산자",
			"로버츠알고리즘","소벨알고리즘","프리윗알고리즘","라플라시안알고리즘","로그알고리즘","도그알고리즘"};
	//히스토그램처리에 대한 목록리스트(문자열 배열)
	private String[] algo4 = {"히스토그램처리","스트레칭","엔드-인","평활화"};
	//색상모델을 RGB->HSV모델로 바꾼 처리에 대한 목록리스트(문자열 배열)
	private String[] algo5 = {"HSV모델처리","채도변환","명도변환","오렌지이미지추출"};
	
	//화소점처리에 대한 목록리스트(algo1)로 패널에 추가할 JComboBox(algo11)를 만든다.
	private JComboBox<String> algo11 = new JComboBox<String>(algo1);
	//기하학처리에 대한 목록리스트(algo2)로 패널에 추가할 JComboBox(algo22)를 만든다.
	private JComboBox<String> algo22 = new JComboBox<String>(algo2);
	//화소영역처리에 대한 목록리스트(algo3)로 패널에 추가할 JComboBox(algo33)를 만든다.
	private JComboBox<String> algo33 = new JComboBox<String>(algo3);
	//히스토그램처리에 대한 목록리스트(algo4)로 패널에 추가할 JComboBox(algo44)를 만든다.
	private JComboBox<String> algo44 = new JComboBox<String>(algo4);
	//HSV모델처리에 대한 목록리스트(algo5)로 패널에 추가할 JComboBox(algo55)를 만든다.
	private JComboBox<String> algo55 = new JComboBox<String>(algo5);
	
	//값을 입력 받을 곳의 명칭..안내문
	private JLabel la1 = new JLabel("para1:");
	private JLabel la2 = new JLabel("para2:");
	
	//영상처리를 위해 받아와야하는 값을 입력할 JTextField
	private JTextField tf1 = new JTextField(10);
	//영상이동처리 시에는 값이 2개 필요하므로
	private JTextField tf2 = new JTextField(10);
	
	//p1에는 JComboBox들이 들어가고
	private JPanel p1 = new JPanel();
	//p2에는 JLabel과 JTextField가 들어간다.
	private JPanel p2 = new JPanel();
	
	//파일을 불러오고 저장하기를 할 버튼 만들기
	//메뉴를 넣을 바(틀)생성
	private JMenuBar mb = new JMenuBar();
	//File이라는 이름의 메뉴버튼생성
	private JMenu file = new JMenu("File");
	//File메뉴버튼 안에 들어갈 JMenuItem생성
	//OPEN은 파일 열기가 될것이고 
	//SAVE는 파일 저장하기가 될 것이다.
	private JMenuItem fopen = new JMenuItem("OPEN");
	private JMenuItem fsave = new JMenuItem("SAVE");
	
	//파일다이얼로그!!파일을 저장하고 열기를 실행할 파일 탐색창을 연다.
	//FileDialog에서 열기는 창의 이름부분에 뜨는 메시지이다. FileDialog.LOAD는 파일을 열게할 FileDialog의 유형이다.
	private FileDialog fdlg1 = new FileDialog(this,"열기",FileDialog.LOAD);
	//FileDialog.SAVE는 당연히 파일을 저장할 유형
	private FileDialog fdlg2 = new FileDialog(this,"저장",FileDialog.SAVE);
	
	// pm은 그림이 들어갈 패널
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
		//내 코드에서는 모든 이벤트가 ActionListener로 돌아가므로 
		//함수 안에서 어떤 변수에서 이벤트가 발생했는지를 알아내는 if문이 필요하다.
		if(e.getSource()==fopen) {
			fdlg1.setVisible(true);
			inputImage();
		}
		else if(e.getSource()==fsave) {
			fdlg2.setVisible(true);
			outputImage();
		}
		else if(e.getSource()==algo11) {
			//JComboBox에서 어떤 부분을 클릭했는지 정보를 가져오기위함
			JComboBox<String> cb = (JComboBox<String>)e.getSource();
			//선택된 아이템의 인덱스를 가져온다.
			int index = cb.getSelectedIndex();
			
			//문자열로 처리하면 값을 합할 수 없고 이어야하기때문에 10이라는 숫자에 인덱스만 붙이면 된다는 꼼수는
			//값이 10을 넘어가게 되면 1011같이 1000을 넘어간 숫자로 변하기 때문에 경우를 나누어서 함수를 호출해주었다.
			if(index>=10) {
				//algoFunction을 통해 switch문으로 처리할 함수로 옮겨간다.
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
		//index로 실행할 함수를 찾아간다.
		//java는 switch문에서 문자열을 비교할 수 있다.
		switch(index) {
		case "101"://동일영상
			equalImage();break;
		case "102"://반전영상
			reverseImage();break;
		case "103"://영상 더하기/빼기
			addImage();break;
		case "104"://영상 곱하기
			gopImage();break;
		case "105"://영상 나누기
			divImage();break;
		case "106"://흑백127기준
			image127();break;
		case "107"://흑백 평균 기준
			avgImage();break;
		case "108"://파라볼라 컵
			paraCupImage();break;
		case "109"://파라볼라 캡
			paraCapImage();break;
		case "110"://감마
			gammaImage();break;
		case "111"://그레이스케일
			grayImage();break;
			
			
		case "201"://상하미러링
			udImage();break;
		case "202"://좌우미러링
			lrImage();break;
		case "203"://영상이동
			swapImage();break;
			
			
		case "301"://엠보싱
			embossingImage();break;
		case "302"://블러링
			blurrImage();break;
		case "303"://샤프닝
			sharpenImage();break;
		case "304"://가우시안
			gaussianImage();break;
		case "305"://고주파 샤프닝
			hpfSharpImage();break;
		case "306"://이동과 차분
			sadImage();break;
		case "307"://유사연산자
			opImage();break;
		case "308"://로버츠 알고리즘
			robertsImage();break;
		case "309"://소벨 알고리즘
			sobelImage();break;
		case "310"://프리윗 알고리즘
			prewittImage();break;
		case "311"://라플라시안 알고리즘
			laplacianImage();break;
		case "312"://로그 알고리즘
			logImage();break;
		case "313"://도그 알고리즘
			dogImage();break;

			
		case "401"://스트레칭
			strechImage();break;
		case "402"://엔드-인
			endInImage();break;
		case "403"://평활화
			equalizeImage();break;
			
			
		case "501"://채도변환
			saturImage();break;
		case "502"://명도변환
			intensityImage();break;
		case "503"://오렌지이미지추출
			orangeImage();break;
		}
	}
	
	///////////////////////////////////////////////////
	////파일입출력////////////////////////////////////////
	///////////////
	//전역변수부
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
	///메인코드부
	public void inputImage() {//선택한 이미지를 입력배열에 입력 받아 출력하는 함수
		try {
		//FileDialoge에서 열고자 하는 파일의 경로를 가져와 File클래스에 저장하는코드이다.
		//getDirectory()는 파일의 디렉터리 경로를 가져온다.
		//getFile()은 선택한 파일명을 가져온다.
		inFp =new File(fdlg1.getDirectory()+fdlg1.getFile());
		//File클래스에 저장한 경로에 따른 파일을 BufferedImage클래스에 저장한다.
		bImage = ImageIO.read(inFp);
		
		//BufferedImage에 저장된 그림은 가로 세로가 반대로 되어있다.
		//이렇게 반대로 하지않으면 배열 범위 초과 오류가 난다.
		inH = bImage.getWidth();
		inW = bImage.getHeight();
		
		inImage = new int[3][inH][inW];
		
		for(int i=0;i<inH;i++) {
			for(int k=0;k<inW;k++) {
				//BufferedImage에 저장된 이미지의 한 픽셀의 RGB값을 가져온다.
				//이 값은 0xf0ed77이런식으로 구성되어있다.
				int rgb=bImage.getRGB(i,k);
				
				//RGB값을 R값과 G값과 B값으로 나누어 변수에 값을 입력 받는다.
				int r =(rgb>>16)&0xFF;
				int g =(rgb>>8)&0xFF;
				int b=(rgb>>0)&0xFF;
				
				//RGB값을 입력 배열에 입력
				inImage[0][i][k]=r;
				inImage[1][i][k]=g;
				inImage[2][i][k]=b;
				
				//사진 출력을 위해 다시 버퍼에 입력한다.
				//이 번거로운 과정을 거치는 이유는 drawImage가 배열을 받아 그리지 못하고
				//버퍼에 입력된 이미지를 그릴 수 있기 때문이다.
				bImage.setRGB(i, k, rgb);
			}
		}
		//repaint()를 통해 버퍼에 입력된 이미지를 출력한다.
		repaint();
		
		}catch(Exception e){
			System.out.println("BBBBB255줄"+e);
		}
		
		
	}
	class MyPanel extends JPanel{//패널을 상속받은 클래스를 만들어 paintComponent()함수를 추가한다.
		//repaint()하면 이 함수가 호출되는 것이다.
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//버퍼에 있는 이미지를 출력함
			g.drawImage(bImage,50,100, this);
		}
	}
	public void displayImage() {//영상처리한 이미지를 출력하는 함수 
		try {
			
			for(int i=0;i<outH;i++) {
				for(int k=0;k<outW;k++) {
					//outImage에 들어있는 픽셀값을 잘 R,G,B를 나눠서 넣는다.
					int r = outImage[0][i][k];
					int g = outImage[1][i][k];
					int b = outImage[2][i][k];
					//RGB픽셀값들을 합쳐서 하나로 만든 값을 저장할 변수
					int px=0;
					px=px|(r<<16);
					px=px|(g<<8);
					px=px|(b<<0);
					//다시 0xff08e6같은 수로 변하고 이미지를 저장할 버퍼에 넣는다.
					bImage.setRGB(i, k, px);
				}
			}

			//다시 그림
			repaint();
			
			}catch(Exception e){
				System.out.println("kkkkkkk321줄"+e);
			}
	}
	

	public void outputImage() {//영상처리한 이미지를 원하는 위치에 저장하는 함수
		try {
			//이미지를 위치에 저장한다. 그냥 저장하면  확장자없는 파일로 나오기 때문에 저장시의 확장자를 임의로 jpg로 맞춰준다.
			outFp=new File(fdlg2.getDirectory()+fdlg2.getFile()+".jpg");
			//RGB값을 입력받을 버퍼라는 의미(BufferedImage.TYPE_INT_RGB)
			BufferedImage oImage = new BufferedImage(outH,outW,BufferedImage.TYPE_INT_RGB);
			for(int i=0;i<outH;i++) {
				for(int k=0;k<outW;k++) {
					//저장하고 싶은 이미지의 배열을 rgb에 저장한다.
					int r = outImage[0][i][k];
					int g = outImage[1][i][k];
					int b = outImage[2][i][k];
					//픽셀을 하나로 합친다.
					int px=0;
					px=px|(r<<16);
					px=px|(g<<8);
					px=px|(b<<0);
					//출력할 버퍼에 픽셀값 하나를 넣는다.
					oImage.setRGB(i, k, px);
				}
			}
			//버퍼에 담긴 이미지를 jpg형식으로 선택한 파일 위치에 저장한다.
			ImageIO.write(oImage,"jpg",outFp);
		}catch(Exception e) {
			System.out.println("CCCCCC245"+e);
		}
	}
	
	////////////////////////////////////////////////
	////화소점처리/////////////////////////////////////
	public void equalImage() {//동일영상 알고리즘
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
	public void reverseImage() {//반전영상 알고리즘
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
	public void addImage() {//영상 밝게/어둡게 알고리즘
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
	public void gopImage() {//영상 곱하기 알고리즘
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
	public void divImage() {//영상 나누기 알고리즘
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
	public void image127() {//흑백 127기준변환 알고리즘
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
	public void avgImage() {//흑백 평균기준 변환 알고리즘
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
	public void paraCupImage() {//파라볼라 컵 알고리즘
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
	public void paraCapImage() {//파라볼라 캡 알고리즘
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
	public void gammaImage() {//감마 알고리즘
		outH =inH;
		outW =inW;
		// 메모리 할당
		outImage = new int[3][outH][outW];
		/// ** Image Processing Algorithm **
		double value = Double.parseDouble(tf1.getText());
		if(value <0)
			value =1/(1-value);
		else
			value +=1;
		//감마 변환
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
	/////기하학처리////////////////////////////////
	public void udImage() {//좌우 반전 알고리즘
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
	public void lrImage() {//상하 반전 알고리즘
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
	public void swapImage() {//영상이동 알고리즘
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
	////화소영역처리//////////////////////////////////
	public void embossingImage() {//엠보싱 알고리즘
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
	public void blurrImage() {//블러링 알고리즘
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
	public void sharpenImage() {//샤프닝 알고리즘
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
	public void gaussianImage() {//가우시안 알고리즘
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
	public void hpfSharpImage() {//고주파샤프닝 알고리즘
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
	public void sadImage() {//이동과 차분 알고리즘
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
	public void opImage() {//유사연산자 알고리즘
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
	public void robertsImage() {//로버츠 마스크 알고리즘
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
	public void sobelImage() {//소벨 마스크 알고리즘
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
	public void prewittImage() {//프리윗 마스크 알고리즘
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
	public void laplacianImage() {//라플라시안 알고리즘
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
	public void logImage() {//로그 알고리즘
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
	public void dogImage() {//도그 알고리즘
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
	////히스토그램처리//////////////////////////////
	public void strechImage() {//스트레칭 알고리즘
		outH = inH;
		outW = inW;
		outImage = new int[3][outH][outW];
		float tmpHSVImage[][][]=new float[3][outH][outW];
		
		
		//HSV변환->tmpHSVImage에 저장
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
		//명도에 대한 스트레칭
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
	public void endInImage() {//엔드-인 알고리즘
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
	public void equalizeImage() {//평활화 알고리즘
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
	////HSV변환 함수///////////////////////////////
	public float[] rgb2hsv(float r, float g, float b) {//rgb->hsv변환
		
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
	public float[] hsv2rgb(float h, float s, float v){//hsv->rgb변환
		
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
	////HSV변환 처리//////////////////////////////
	public void saturImage() {//채도 변환 알고리즘
		// (중요!) 출력 이미지의 크기가 결정 ---> 알고리즘에 의존...
	    outH = inH;
	    outW = inW;
	    // 출력 영상의 3차원 메모리 할당
	   	outImage = new int[3][inH][inW];
	    
	    // **** 진짜 영상처리 알고리즘 *****
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
	           	
	                
	                // 채도를 변경하자
	                S = S + s_value;
	                
	                // HSV --> RGB
	                
	                		
	                def = hsv2rgb(H,S,V);
					int R1 = (int) def[0]; 
					int G1 = (int) def[1];
					int B1 = (int) def[2];
	                // 출력 영상에 넣기
	                outImage[0][i][k] = R1;
	                outImage[1][i][k] = G1;
	                outImage[2][i][k] = B1;
	            }
	        }            
	    // ******************************
	        displayImage();
	}
	public void grayImage() {//그레이스케일 알고리즘
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
	public void intensityImage() {//명도 변경 알고리즘
		// (중요!) 출력 이미지의 크기가 결정 ---> 알고리즘에 의존...
	    outH = inH;
	    outW = inW;
	    // 출력 영상의 3차원 메모리 할당
	   	outImage = new int[3][inH][inW];
	    
	    // **** 진짜 영상처리 알고리즘 *****
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
	           	
	                
	                // 채도를 변경하자
	                V = V + v_value;
	                
	                // HSV --> RGB		
	                def = hsv2rgb(H,S,V);
					int R1 = (int) def[0]; 
					int G1 = (int) def[1];
					int B1 = (int) def[2];
					
	                // 출력 영상에 넣기
	                outImage[0][i][k] = R1;
	                outImage[1][i][k] = G1;
	                outImage[2][i][k] = B1;
	            }
	        }            
	    // ******************************
	        displayImage();
	}
	public void orangeImage() {//오렌지 추출 알고리즘
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