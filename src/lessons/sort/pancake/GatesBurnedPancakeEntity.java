package lessons.sort.pancake;

import lessons.sort.pancake.universe.PancakeEntity;
import lessons.sort.pancake.universe.PancakeWorld;

public class GatesBurnedPancakeEntity extends PancakeEntity {

	public void run() {
		this.solve();
	}

	/* BEGIN HIDDEN */
	int getRankOf(int size) {
		for (int rank=0;rank<getStackSize();rank++)
			if (getPancakeRadius(rank) == size)
				return rank;
		return -99; // Well, be robust to border cases 
	}
	boolean isFree(int pos) {
		if (pos == -99)
			return false;
		int radius = getPancakeRadius(pos);
		if (pos>0) {
			int nextRadius = getPancakeRadius(pos-1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos-1)==false || nextRadius == radius+1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos-1)==true )
				return false;
		}
		if (pos<getStackSize()-1) {
			int nextRadius = getPancakeRadius(pos+1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos+1)==true  || nextRadius == radius+1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos+1)==false )
				return false;
		}
		return true;
	}
	boolean isFirst(int pos) {
		if (pos == -99)
			return false;
		int radius = getPancakeRadius(pos);
		if (pos>0) {
			int nextRadius = getPancakeRadius(pos-1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos-1)==false|| nextRadius == radius+1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos-1)==true)
				return false;
		}
		if (pos<getStackSize()-1) {
			int nextRadius = getPancakeRadius(pos+1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos+1)==true|| nextRadius == radius+1&& isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos+1)==false)
				return true;
		}
		return false;
	}
	boolean isLast(int pos) {
		if (pos == -99)
			return false;
		int radius = getPancakeRadius(pos);
		if (pos<getStackSize()-1) {
			int nextRadius = getPancakeRadius(pos+1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos+1)==true|| nextRadius == radius+1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos+1)==false)
				return false;
		}
		if (pos>0) {
			int nextRadius = getPancakeRadius(pos-1);
			if (nextRadius == radius-1 && isPancakeUpsideDown(pos)==false && isPancakeUpsideDown(pos-1)==false|| nextRadius == radius+1&& isPancakeUpsideDown(pos)==true && isPancakeUpsideDown(pos-1)==true)
				return true;
		}
		return false;
	}
	int blockLength() {
		int pos = 0;
		int radius = getPancakeRadius(pos);
		int o = getPancakeRadius(pos+1) - radius;

		if (o != -1 && o != 1) {
			System.out.println("Asked to compute the block length, but the step o is "+o+" instead of +1 or -1. " +
					"The length is then 1, but you are violating a precondition somehow");
			return 1;
		}

		while (pos < getStackSize()-1 && getPancakeRadius(pos+1) == radius + o && isPancakeUpsideDown(pos)==isPancakeUpsideDown(pos+1) && isPancakeUpsideDown(pos)==isPancakeUpsideDown(0)) {
			pos++;
			radius += o;
		}
		return pos+1;
	}
	int debug=0; // 0: silence; 1: which cases; 2: all details
	/* END HIDDEN */

	/* BEGIN TEMPLATE */
	public void solve() {
		/* BEGIN SOLUTION */
		/* cruft to search for an instance exercising all transformations */
		boolean doneA=false;
		boolean doneB=false;
		boolean doneC=false;
		boolean doneD=false;
		boolean doneE=false;
		boolean doneF=false;
		boolean doneG=false;
		boolean doneH=false;
		Integer[] origSizes = new Integer[getStackSize()];
		for (int i=0;i<getStackSize();i++)
			origSizes[i] = getPancakeRadius(i);
		/* end of this cruft */

		int stackSize = getStackSize();

		if (debug>0) {
			System.out.print("{");
			for (int rank=0; rank < stackSize; rank++) 
				System.out.print(""+getPancakeRadius(rank)+", ");
			System.out.println("}");
		}

		while (true) {
			int tRadius = getPancakeRadius(0);
			int posTPlus  = getRankOf(tRadius+1); // returns -99 if non-existent, that is then ignored
			int posTMinus = getRankOf(tRadius-1); 
			int posT = 0;

			if (debug>1) {
				System.out.println("t Radius: "+tRadius);
				for (int rank=0; rank < stackSize; rank++) {
					System.out.print("["+rank+"]="+getPancakeRadius(rank)+"; ");

					if (isFree(rank))
						System.out.print("free    ;");
					else 
						System.out.print("NON-free;");

					if (isFirst(rank))
						System.out.print("first    ; ");
					else 
						System.out.print("NON-first; ");

					if (isLast(rank))
						System.out.print("last    ; ");
					else 
						System.out.print("NON-last; ");

					if(isPancakeUpsideDown(rank)){
						System.out.print("up  ;");
					}else{
						System.out.print("down;");
					}
					if (rank == posTPlus)
						System.out.print("t+1; ");
					if (rank == posTMinus)
						System.out.print("t-1; ");
					if (rank == posT)
						System.out.print("t;" );


					System.out.println();
				}
			}

			if (isFree(posT)) {			
				if (isFree(posTPlus)) { /* CASE A: t and t+o free */
					if (debug>0)
						System.out.println("Case A+");
					if(isPancakeUpsideDown(posT)){
						if(isPancakeUpsideDown(posTPlus)){
							flip(posTPlus+1);
							flip(1);
							flip(posTPlus+1);
						}
						flip(posTPlus);
					}else if(!isPancakeUpsideDown(posT)){
						if(isPancakeUpsideDown(posTPlus)){
							flip(posTPlus+1);
							flip(1);
							flip(posTPlus+1);
						}
						flip(1);
						flip(posTPlus);
					}
					doneA = true;
				} else if (isFree(posTMinus)) { /* CASE A: t and t-o free */
					if (debug>0)
						System.out.println("Case A-");
					if(isPancakeUpsideDown(posT) && isPancakeUpsideDown(posTMinus)){
						flip(posTMinus+1);
						flip(1);
						flip(posTMinus);
					}else if(isPancakeUpsideDown(posT) && !isPancakeUpsideDown(posTMinus)){
						flip(posTMinus+1);
						flip(posTMinus);
					}else if(!isPancakeUpsideDown(posT) && isPancakeUpsideDown(posTMinus)){
						flip(1);
						flip(posTMinus+1);
						flip(1);
						flip(posTMinus);
					}else if(!isPancakeUpsideDown(posT) && !isPancakeUpsideDown(posTMinus)){
						flip(1);
						flip(posTMinus+1);
						flip(posTMinus);
					}
					doneA = true;

				} else if (isFirst(posTPlus)) { /* CASE B: t free, t+o first element */
					if (debug>0)
						System.out.println("Case B+");
					if(!isPancakeUpsideDown(posT)){
						flip(1);
					}
					flip(posTPlus);
					doneB = true;
				} else if (isFirst(posTMinus)) { /* CASE B: t free, t-o first element */
					if (debug>0)
						System.out.println("Case B-");
					if(isPancakeUpsideDown(posT)){
						flip(1);
					}
					flip(posTMinus);
					doneB = true;

				} else if (Math.min(posTPlus,posTMinus) != -99) { /* CASE C: t free, but both t+o and t-o are last elements */
					if (debug>0)
						System.out.println("Case C");
					if(!isPancakeUpsideDown(posT) && posTMinus<posTPlus || isPancakeUpsideDown(posT) && posTMinus>posTPlus){
						flip(1);
					}
					flip(Math.min(posTPlus,posTMinus) );
					flip(Math.min(posTPlus,posTMinus) - 1);
					flip(Math.max(posTPlus,posTMinus) + 1);
					flip(Math.min(posTPlus,posTMinus) - 1);
					doneC = true;

				} else {
					if (debug>0)
						System.out.println("Case Cbis");
					if(isPancakeUpsideDown(posT) && posTMinus==-99 || !isPancakeUpsideDown(posT) && posTPlus==-99){
						flip(1);
					}
					flip(Math.max(posTPlus,posTMinus) + 1);
					flip(Math.max(posTPlus,posTMinus) );
					doneC = true;
				}

			} else { // t is in a block
				if (blockLength() == stackSize) { // Done!
					if (tRadius != 1) // all reverse 
						flip(stackSize);
					if (doneA && doneB && doneC && doneD && doneE && doneF && doneG && doneH && ((PancakeWorld)world).wasRandom) {
						System.out.println("BINGO! This instance is VERY interesting as it experiences every cases of the algorithm.\nPLEASE REPORT IT. PLEASE DONT LOSE IT.");
						System.out.print("{");
						for (int rank=0; rank < stackSize; rank++) 
							System.out.print(""+origSizes[rank]+", ");
						System.out.println("}");
					}
					return;
				}

				if (isFree(posTPlus)) {          /* CASE D: t in a block, t+1 free */
					if (debug>0)
						System.out.println("Case D+");
					if(isPancakeUpsideDown(posTPlus)){
						flip(posTPlus+1);
						flip(1);
						flip(posTPlus+1);
					}
					flip(posTPlus);
					doneD = true;

				} else if (isFree(posTMinus)) {  /* CASE D: t in a block, t-1 free */
					if (debug>0)
						System.out.println("Case D-");
					if(!isPancakeUpsideDown(posTMinus)){
						flip(posTMinus+1);
						flip(1);
						flip(posTMinus+1);
					}
					flip(posTMinus);
					doneD = true;

				} else if (isFirst(posTPlus)) {  /* CASE E: t in a block, t+1 first element */
					if (debug>0)
						System.out.println("Case E+");
					flip(posTPlus);
					doneE = true;

				} else if (isFirst(posTMinus)) { /* CASE E: t in a block, t-1 first element */
					if (debug>0)
						System.out.println("Case E-");
					flip(posTMinus);
					doneE = true;

				} else if (isLast(posTPlus) && posTPlus != 1) { /* CASE F+: t in a block, t+1 last element */
					doneF = true;
					if (debug>0)
						System.out.println("Case F+");
					flip(blockLength());
					flip(posTPlus + 1);
					int newPos = getRankOf(tRadius);
					if (newPos>0)
						flip(newPos);

				} else if (isLast(posTMinus) && posTMinus != 1) { /* CASE F-: t in a block, t-1 last element */
					doneF = true;
					if (debug>0)
						System.out.println("Case F-");
					flip(blockLength());
					flip(posTMinus + 1);
					int newPos = getRankOf(tRadius);
					if (newPos>0)
						flip(newPos);
				} else {
					int k = blockLength()-1;
					int o = getPancakeRadius(1) - tRadius;
					int pos = getRankOf(tRadius+(k+1)*o);
					if (isFree(pos) || isFirst(pos)) {
						doneG = true;
						if (debug>0)
							System.out.println("Case G");
						if((isPancakeUpsideDown(pos) && o>0) || (!isPancakeUpsideDown(pos) && o<0)){
							flip(pos+1);
							flip(1);
							flip(pos+1);
						}
						if(k+1 != pos){
							flip(k+1);
							flip(pos);
						}
					} else {
						doneH = true;
						if (debug>0)
							System.out.println("Case H");
						flip(pos+1);
						flip(getRankOf(tRadius+k*o));
					}
				}
			}
		}
		/* END SOLUTION */
	}
	/* END TEMPLATE */

}
