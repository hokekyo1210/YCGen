import java.security.SecureRandom;

public class YuruconGen {
	
	private static SecureRandom r1;
	private int N;
	private int M;
	private char[][] board = null;
	private int S,C,X;
	
	public YuruconGen(int N){
		this.N = N;
	}
	
	private final int[] DIRX = {-1,0,1,0};
	private final int[] DIRY = {0,-1,0,1};
	public boolean generate(){
		if(N<10||N>30){
			System.out.println("N must be an integer between 10 and 30.");
			return false;
		}
		board = new char[30][30];
		for(int h = 0;h<30;h++)
		for(int w = 0;w<30;w++){
			board[w][h] = '#';
		}
		for(int h = 0;h<N;h++)
		for(int w = 0;w<N;w++){
			board[w][h] = '.';
		}
		
		M = (r1.nextInt((N/2)-2))+3;
		
		for(int i = 0;i<M;i++)
		for(int j = 0;j<2;j++)
		while(true){
			int x = r1.nextInt(N);
			int y = r1.nextInt(N);
			if(board[x][y]!='.')continue;
			boolean can = true;
			for(int d = 0;d<4;d++){
				int tx = x+DIRX[d];
				int ty = y+DIRY[d];
				if(tx<0||ty<0||tx>=N||ty>=N)continue;
				if(board[tx][ty]!='.')can = false;
			}
			if(!can)continue;
			if(j==0)
				board[x][y] = 'S';
			if(j==1)
				board[x][y] = 'G';
			break;
		}
		
		int white = N*N-2*M;
		int perc = r1.nextInt(201)+100;
		int cross = (int)(white*perc*0.0001);
		int pers = r1.nextInt(61)+10;
		int straight = (int)(white*pers*0.01);
		int curve = white-(cross+straight);
		S = straight;
		C = curve;
		X = cross;
		
		return true;
	}
	
	public char[][] getResult(){
		return board;
	}
	
	public int getN(){return N;}
	public int getM(){return M;}
	public int getS(){return S;}
	public int getC(){return C;}
	public int getX(){return X;}

	public static void main(String[] args) throws Exception {
		
		int N = -1;
		long seed = -1;
		boolean meta = false;
		
		for(int i = 0; i<args.length; i++)
		{
			try{
				
			if(args[i].equals("-N"))
				N = Integer.parseInt(args[++i]);
			if(args[i].equals("-seed"))
				seed = Long.parseLong(args[++i]);
			if(args[i].equals("-meta"))
				meta = true;
			
			}catch(Exception e){
				System.out.println("NumberFormatException.["+args[i]+"]");
				return;
			}
		}
		
		if(seed<=0)seed = 1;
		r1 = SecureRandom.getInstance("SHA1PRNG");
		r1.setSeed(seed);
		if(N==-1)
			N = (r1.nextInt(21))+10;
		
		YuruconGen gen = new YuruconGen(N);
		if(!gen.generate())return;
		
		char[][] result = gen.getResult();
		if(meta){
			System.out.println("seed:"+seed);
			System.out.println("N:"+gen.getN());
			System.out.println("S,G:"+gen.getM());
		}
		for(int h = 0;h<30;h++){
			for(int w = 0;w<30;w++){
				System.out.print(result[w][h]);
			}
			System.out.print("\r\n");
		}
		System.out.print(gen.S+" "+gen.C+" "+gen.X);
		System.out.print("\r\n");
	}

}
