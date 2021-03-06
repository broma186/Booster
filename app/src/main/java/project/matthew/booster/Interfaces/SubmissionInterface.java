package project.matthew.booster.Interfaces;

/**
 * Created by Matthew on 4/05/2018.
 */

public interface SubmissionInterface {
    public String isFormValid();
    public void clearResultsAndStartAgain();
    public void clearQuestionsAndAnswers();
    public void clearQuestionnaireCompleteFlag();
    public void startAppAgain();
    public void showSuccessDialog();
    public void showFailureToast();
}
