import java.util.Scanner;

public class CacheSim {

	private static final int BLOKEAK = 8;
	private static final int TCM = 2;
	private static final int TMN = 20;
	
	private static int hitza;
	private static int blokea;
	private static int tag;
	private static int multzo;
	private static int helbide;
	private static Integer[][] cacheTaula;
	private static boolean hutsegitea = true;
	private static int multzo_kop;
	private static int bloke_kop_multzoko;
	
	private static int ordezk_polit;
	private static int irakurri;
	
	public static void main(String[] args) {
		
		int hitz_tam;
		int blok_tam;
		
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
		
		multzo_kop = BLOKEAK / bloke_kop_multzoko;
		
		
		cacheTaula = new Integer[8][5];
		
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
			
			multzoKalkulua();
			
			helbideaCacheTaulanSartu();
			
			int t_totala = 0;
			
			if(hutsegitea) {
				System.out.println("HUTSEGITEA");
				System.out.println("Atzipen-denbora: cachean bilatzea, "+ TCM + " -- bloke bat eramatea"
						+ "(M>C,C>M), " +(TMN +hitz_blokeko));
				t_totala = TCM+TMN+hitz_blokeko;
			}
			else {
				System.out.println("ASMATZEA");
				System.out.println("Atzipen-denbora: cachean bilatzea, "+ TCM);
				t_totala = TCM;
			}
			
			System.out.println("T_atz: " + t_totala + " ziklo");
			
			cacheTaulaSortu();
			
			System.out.print("Helbidea (byte) (-1 uzteko) >");
			helbide = scan.nextInt();
		}
		
		
		
	}
	
	private static void cacheTaulaSortu() {
		
		
		System.out.println("  okup ald  tag  ord ||  blokea");
		System.out.println("----------------------------------");
		
		for (int i = 0; i < multzo_kop; i++) {
			
			for (int j = 0; j < bloke_kop_multzoko; j++) {
				
				for (int j2 = 0; j2 < cacheTaula[j].length; j2++) {
					
					if(j2 == 4) {
						
						if(cacheTaula[i*bloke_kop_multzoko+j][j2] == null)
							System.out.print(" ||   ---  ");
						else {
							System.out.print(" ||   b" + cacheTaula[i*bloke_kop_multzoko+j][j2] + "  ");
						}
					}
					else if(cacheTaula[i*bloke_kop_multzoko+j][j2] == null) {
						System.out.print("   0 ");
					}
					else {
						System.out.print("   " + cacheTaula[i*bloke_kop_multzoko+j][j2] + " ");
					}
				}
				
				System.out.println("");
			}
			
			System.out.println("----------------------------------");
			
			
		}
		
	}

	private static void multzoKalkulua() {
		
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
	
	
	private static void helbideaCacheTaulanSartu() {
		
		if(multzo_kop != 8) { 
			int i = 0;
			boolean aurkitua = false;
			while(i < bloke_kop_multzoko && !aurkitua) {
				
				if(cacheTaula[bloke_kop_multzoko*multzo +i][4] == null) {
					
					cacheTaula[bloke_kop_multzoko*multzo +i][0] = 1;
					cacheTaula[bloke_kop_multzoko*multzo+i][1] = 1;
					cacheTaula[bloke_kop_multzoko*multzo+i][2] = tag;
					cacheTaula[bloke_kop_multzoko*multzo+i][3] = 3;
					cacheTaula[bloke_kop_multzoko*multzo+i][4] = blokea;
					
					hutsegitea = true;
					aurkitua = true;
				}
				else if(cacheTaula[bloke_kop_multzoko*multzo +i][4] == blokea) {
					
					hutsegitea = false;
					aurkitua = true;
				}
				else if(cacheTaula[bloke_kop_multzoko*multzo +i][4] != null && cacheTaula[bloke_kop_multzoko*multzo +i][4] != blokea) {
					
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
