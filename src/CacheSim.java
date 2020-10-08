import java.util.Scanner;

public class CacheSim {

	private static int hitza;
	private static int blokea;
	private static int tag;
	private static int multzo;
	private static int helbide;
	//private static int[][] cacheTaula;
	private static boolean hutsegitea = true;
	private static final int BLOKEAK = 8;
	private static int ordezk_polit;
	private static int irakurri;
	public static void main(String[] args) {
		
		int hitz_tam;
		int blok_tam;
		int bloke_kop_multzoko;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Hitzen tamaina (byteak): 4 - 8 > ");
		hitz_tam = scan.nextInt();
		System.out.print("Blokeen tamaina (byteak): 32 - 64 >");
		blok_tam = scan.nextInt();
		System.out.print("Multzoen tam. (blokeak): 1-2-4-8 >");
		bloke_kop_multzoko = scan.nextInt();
		System.out.print("Ordezk.-polit.: 0(FIFO) - 1(LRU) >");
		ordezk_polit = scan.nextInt();
		
		System.out.print("Helbidea (byte) (-1 uzteko) >");
		helbide = scan.nextInt();
		
		int multzo_kop = BLOKEAK / bloke_kop_multzoko;
		
		
		Integer[][] cacheTaula = new Integer[8][5];
		
		while (helbide != -1) {
			
			System.out.print("Load (0) / Store (1) >");
			irakurri = scan.nextInt();
			
			// zer hitz den emandako helbidea
			hitza = helbide / hitz_tam;
			
			// zenbat hitz bloke bakoitzean (bloke baten byteak zati hitz baten byteak)
			int hitz_blokeko = blok_tam /hitz_tam;
			
			// zer blokeri dagokion helbide horri
			blokea = hitza / hitz_blokeko;
			
			
			
			
			System.out.println("Helbidea: " + helbide + " - Hitza: " + hitza + " - Blokea: " + blokea +
					" (" + hitza + " - " + (hitza+BLOKEAK-1) + " hitzak)");
			
			multzoKalkulua(multzo_kop);
			
			helbideaCacheTaulanSartu(multzo_kop,cacheTaula);
			
			if(hutsegitea) {
				System.out.println("HUTSEGITEA");
			}
			else {
				System.out.println("ASMATZEA");
			}
			
			System.out.print("Helbidea (byte) (-1 uzteko) >");
			helbide = scan.nextInt();
		}
		
		
		
	}
	
	public static void multzoKalkulua(int multzo_kop) {
		
		if(multzo_kop == 1) {
			
			tag = blokea;
			System.out.println("Tag: " +tag);
			
		}
		else {
			
			multzo = blokea % multzo_kop;
			
			tag = blokea / multzo_kop;
			
			if(multzo_kop == 2 || multzo_kop == 4) {
				System.out.println("Multzoa: " + multzo + " - Tag: " + tag);
			}
			else {
				System.out.println("Blokea: " + multzo + " - Tag: " + tag);
			}
			
		}
	}
	
	
	public static void helbideaCacheTaulanSartu(int multzo_kop, Integer[][] cacheTaula) {
		
		if(multzo_kop != 8) { 
			int i = 0;
			boolean aurkitua = false;
			while(i < cacheTaula.length / multzo_kop && !aurkitua) {
				
				if(cacheTaula[(cacheTaula.length / multzo_kop)*multzo +i][4] == null) {
					
					cacheTaula[(cacheTaula.length / multzo_kop)*multzo +i][0] = 1;
					cacheTaula[(cacheTaula.length / multzo_kop)*multzo+i][1] = 1;
					cacheTaula[(cacheTaula.length / multzo_kop)*multzo+i][2] = tag;
					cacheTaula[(cacheTaula.length / multzo_kop)*multzo+i][3] = 3;
					cacheTaula[(cacheTaula.length / multzo_kop)*multzo+i][4] = blokea;
					
					hutsegitea = true;
					aurkitua = true;
				}
				else if(cacheTaula[(cacheTaula.length / multzo_kop)*multzo +i][4] == blokea) {
					
					hutsegitea = false;
					aurkitua = true;
				}
				else if(cacheTaula[(cacheTaula.length / multzo_kop)*multzo +i][4] != null && cacheTaula[(cacheTaula.length / multzo_kop)*multzo +i][4] != blokea) {
					
					aurkitua = false;
				}
				i++;
			}
		}
		else {
			
			if(cacheTaula[multzo][4] == null) {
				
				cacheTaula[multzo][0] = 1;
				cacheTaula[multzo][1] = 1;
				cacheTaula[multzo][2] = tag;
				cacheTaula[multzo][3] = 0;
				cacheTaula[multzo][4] = blokea;
				
				hutsegitea = true;
			}
			
			
		}
				
	}

}
