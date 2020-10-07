import java.util.Scanner;

public class CacheSim {

	public static void main(String[] args) {
		
		final int BLOKEAK = 8;
		int hitz_tam;
		int blok_tam;
		int multz_tam;
		int helbide;
		Boolean ordezk_polit;
		Boolean irakurri;
		
		int hitza;
		int blokea;
		int tag;
		int multzo;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Hitzen tamaina (byteak): 4 - 8 > ");
		hitz_tam = scan.nextInt();
		System.out.print("Blokeen tamaina (byteak): 32 - 64 >");
		blok_tam = scan.nextInt();
		System.out.print("Multzoen tam. (blokeak): 1-2-4-8 >");
		multz_tam = scan.nextInt();
		System.out.print("Ordezk.-polit.: 0(FIFO) - 1(LRU) >");
		ordezk_polit = scan.nextBoolean();
		
		System.out.print("Helbidea (byte) (-1 uzteko) >");
		helbide = scan.nextInt();
		
		while (helbide != -1) {
			
			System.out.print("Load (0) / Store (1) >");
			irakurri = scan.nextBoolean();
			
			hitza = helbide / hitz_tam;
			
			// zenbat hitz bloke bakoitzean
			int hitz_blokeko = blok_tam /hitz_tam;
			
			// zer blokeri dagokion helbide horri
			blokea = hitza / hitz_blokeko;
			
			int multzo_kop;
			
			multzo = blokea % (BLOKEAK / multz_tam);
			
			tag = blokea / (BLOKEAK / multz_tam);
			
			System.out.print("Helbidea (byte) (-1 uzteko) >");
			helbide = scan.nextInt();
		}
	}

}
