package jlm.universe.bat;

import java.util.List;
import java.util.Vector;

import jlm.core.model.lesson.ExerciseTemplatingEntity;
import jlm.core.model.lesson.Lesson;
import jlm.universe.World;

public abstract class BatExercise extends ExerciseTemplatingEntity {
	public static final boolean INVISIBLE = false;
	public static final boolean VISIBLE = true;
	
	public BatExercise(Lesson lesson) {
		super(lesson);
	}

	protected void setup(World[] ws) {
		if (ws.length > 1)
			throw new RuntimeException("Bat exercises must have at most one world");
		
		String entName="no name";
		entName = ws[0].getName();
		
		for (BatTest t : ((BatWorld)ws[0]).tests)
			run(t);
		
		super.setup(ws,entName,
				"import jlm.universe.bat.BatEntity; "+
		        "import jlm.universe.bat.BatWorld; "+
		        "import jlm.universe.bat.BatTest; "+
		        "import jlm.universe.World; "+
		        "public class "+entName+" extends BatEntity { ");
	}
	
	@Override
	public void runDemo(List<Thread> runnerVect){
		/* No demo in bat exercises */
	}

	public abstract void run(BatTest t);
	
	@Override 
	public void mutateCorrection(WorldKind kind) {
		Vector<World> worlds;
		switch (kind) {
		case INITIAL: worlds = initialWorld; break;
		case CURRENT: worlds = currentWorld; break;
		case ANSWER:  worlds = answerWorld;  break;
		default: throw new RuntimeException("kind is invalid: "+kind);
		}

		for (BatTest t : ((BatWorld)worlds.get(0)).tests) 
			t.objectiveTest = true;
	}
}
