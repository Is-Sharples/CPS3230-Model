import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class LoginTester implements FsmModel {

    private SUT systemUnderTest = new SUT();

    private LoginStates state = LoginStates.Login;
    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean b) {
        if(b){
            systemUnderTest = new SUT();
        }
        state = LoginStates.Login;
    }

    //Transitions
    public boolean loginFunctionGuard(){return getState().equals(LoginStates.Login);}
    public @Action void loginFunction() {
        state = LoginStates.AlertScreen;
        assertEquals(true,SUT.LoginFunction(true));
    }

    public boolean loginFailedFunctionGuard(){return getState().equals(LoginStates.Login);}
    public @Action void loginFunctionFailed(){
        assertEquals(false,SUT.LoginFunction(false));
    }

    public boolean logoutFromAlertScreenGuard(){return  getState().equals(LoginStates.AlertScreen);}
    public @Action void logoutFunction(){
        state = LoginStates.Login;
        assertEquals(true,systemUnderTest.LogoutFunction());
    }

    @Test
    public void LoginTesterRunner() {
        final GreedyTester tester = new GreedyTester(new LoginTester()); //Creates a test generator that can generate random walks. A greedy random walk gives preference to transitions that have never been taken before. Once all transitions out of a state have been taken, it behaves the same as a random walk.
        tester.setRandom(new Random()); //Allows for a random path each time the model is run.
        tester.buildGraph(); //Builds a model of our FSM to ensure that the coverage metrics are correct.
        tester.addListener(new StopOnFailureListener()); //This listener forces the test class to stop running as soon as a failure is encountered in the model.
        tester.addListener("verbose"); //This gives you printed statements of the transitions being performed along with the source and destination states.
        tester.addCoverageMetric(new TransitionPairCoverage()); //Records the transition pair coverage i.e. the number of paired transitions traversed during the execution of the test.
        tester.addCoverageMetric(new StateCoverage()); //Records the state coverage i.e. the number of states which have been visited during the execution of the test.
        tester.addCoverageMetric(new ActionCoverage()); //Records the number of @Action methods which have ben executed during the execution of the test.
        tester.generate(5); //Generates 500 transitions
        tester.printCoverage(); //Prints the coverage metrics specified above.
    }
}
