package src;

import aima.search.framework.GoalTest;

public class CentralsGoalTest implements GoalTest {

	public boolean isGoalState(Object eState) {

		// En búsqued local desconocemos el estado final, por lo tanto siempre devuelve false
		return true;

	}
	
}
