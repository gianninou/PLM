package lessons.sort;

import jlm.lesson.Lesson;
import universe.sort.SortingExercise;
import universe.sort.SortingWorld;

public class ExInsertionSort extends SortingExercise {

	public ExInsertionSort(Lesson lesson) {
		super(lesson);
		name = "Tris par insertion";
		SortingWorld[] myWorlds = new SortingWorld[2];
		myWorlds[0] = new SortingWorld("Functional test",10);
		myWorlds[1] = new SortingWorld("Performance test (200 elms)",200);

		addEntityKind(myWorlds, new AlgInsertionSort(), "InsertionSort");  
		addEntityKind(myWorlds, new AlgShellSort(), "ShellSort");
		
		setup(myWorlds);
	}
}