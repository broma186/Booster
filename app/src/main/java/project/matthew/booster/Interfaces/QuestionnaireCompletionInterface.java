package project.matthew.booster.Interfaces;

/**
 * Created by Matthew on 2/05/2018.
 */

public interface QuestionnaireCompletionInterface {
    public boolean checkDone();
    public void setScore(int score);
    public int getScore();
    public void setInvestorType(String investorType);
    public String getInvestorType();
    public void setFund(String fund);
    public String getFund();
}
