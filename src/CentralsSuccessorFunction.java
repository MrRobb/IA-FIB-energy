package src;

import IA.Energia.Central;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.*;

public class CentralsSuccessorFunction implements SuccessorFunction {

	public void funcionamientoCentrales(CentralsRepresentation state, Integer A, Integer B, Integer C) {

	}

	public List getSuccessors(Object node) {

		ArrayList ret = new ArrayList();

		CentralsRepresentation state = (CentralsRepresentation)node;

		// Por cada cliente
		for (int clientID_old = 0; clientID_old < state.representationClientes.length; clientID_old++) {

			// Asignar a otra central
			int centralID_old = state.representationClientes[clientID_old];
			for (int centralID_new = 0; centralID_new < state.representationCentrales.length; centralID_new++) {

				if (state.canAssign(centralID_new, clientID_old)) {
					CentralsRepresentation succ = new CentralsRepresentation(state);
					succ.assign(centralID_old, clientID_old, centralID_new);

					succ.hCentral_new = centralID_new;
					succ.hCentral_old = centralID_old;
					succ.hCliente_old = clientID_old;
					succ.hCliente_new = -1;

					Integer A = 0;
					Integer B = 0;
					Integer C = 0;
					for (int centralID = 0; centralID < CentralsRepresentation.centrals.size(); centralID++) {
						Central cen = CentralsRepresentation.centrals.get(centralID);
						if (succ.representationCentrales[centralID] != 0) {
							if (cen.getTipo() == Central.CENTRALA) {
								A++;
							}
							else if (cen.getTipo() == Central.CENTRALB) {
								B++;
							}
							else {
								C++;
							}
						}
					}
					CentralsHeuristicFunction hf = new CentralsHeuristicFunction();
					Successor successor = new Successor(clientID_old + ": " + centralID_old + " -> " + centralID_new + " & h = " + hf.getHeuristicValue(succ) + " cen = " + A + " " + B + " " + C, succ);

					ret.add(successor);
				}
			}

			for (int clientID_new = clientID_old + 1; clientID_new < state.representationClientes.length; clientID_new++) {

				int centralID_new = state.representationClientes[clientID_new];
				if (state.canSwap(clientID_old, clientID_new)) {

					CentralsRepresentation succ = new CentralsRepresentation(state);
					succ.swap(clientID_old, clientID_new);

					succ.hCentral_new = centralID_new;
					succ.hCentral_old = centralID_old;
					succ.hCliente_old = clientID_old;
					succ.hCliente_new = clientID_new;

					Integer A = 0;
					Integer B = 0;
					Integer C = 0;
					for (int centralID = 0; centralID < CentralsRepresentation.centrals.size(); centralID++) {
						Central cen = CentralsRepresentation.centrals.get(centralID);
						if (succ.representationCentrales[centralID] != 0) {
							if (cen.getTipo() == Central.CENTRALA) {
								A++;
							}
							else if (cen.getTipo() == Central.CENTRALB) {
								B++;
							}
							else {
								C++;
							}
						}
					}

					CentralsHeuristicFunction hf = new CentralsHeuristicFunction();
					Successor successor = new Successor(clientID_old + ": " + centralID_old + " -> " + centralID_new +
							", " + clientID_new + ": " + centralID_new + " -> " + centralID_old + " & h = " + hf.getHeuristicValue(succ) + " cen = " + A + " " + B + " " + C, succ);

					ret.add(successor);
				}
			}
		}

		System.out.println("nSucc = " + ret.size());
		return ret;
	}
}