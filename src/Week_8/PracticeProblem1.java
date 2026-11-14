package Week8;

import java.util.*;

/**
 * Practice Problem 1: Online Examination System Design and Implementation
 *
 * TASK:
 * Design and implement an online examination system:
 * 1. Examination manages questions (MultipleChoiceQuestion, TrueFalseQuestion, etc.).
 * 2. Student starts exam, answers questions, and submits attempt.
 * 3. Once submitted, answers cannot be changed.
 * 4. Result is evaluated automatically upon submission.
 */
public class PracticeProblem1 {

    public interface Question {
        String getId();
        String getPrompt();
        boolean checkAnswer(String response);
    }

    public static class MultipleChoiceQuestion implements Question {
        private final String id;
        private final String prompt;
        private final String correctAnswer;

        public MultipleChoiceQuestion(String id, String prompt, String correctAnswer) {
            this.id = id;
            this.prompt = prompt;
            this.correctAnswer = correctAnswer;
        }

        @Override
        public String getId() { return id; }

        @Override
        public String getPrompt() { return prompt; }

        @Override
        public boolean checkAnswer(String response) {
            return correctAnswer.equalsIgnoreCase(response.trim());
        }
    }

    public static class Examination {
        private final String title;
        private final List<Question> questions;

        public Examination(String title) {
            this.title = title;
            this.questions = new ArrayList<>();
        }

        public void addQuestion(Question q) {
            questions.add(q);
        }

        public String getTitle() { return title; }

        public List<Question> getQuestions() { return questions; }
    }

    public static class Attempt {
        private final Examination examination;
        private final Map<String, String> responses;
        private boolean submitted;
        private int score;

        public Attempt(Examination examination) {
            this.examination = examination;
            this.responses = new HashMap<>();
            this.submitted = false;
            this.score = 0;
            System.out.println("Examination '" + examination.getTitle() + "' started by Student.");
        }

        public void answerQuestion(String questionId, String answer) {
            if (submitted) {
                System.out.println("Cannot change answers: attempt has already been submitted.");
                return;
            }
            responses.put(questionId, answer);
            System.out.println("Question " + questionId + " answered with '" + answer + "'.");
        }

        public void submit() {
            if (submitted) {
                System.out.println("Attempt already submitted.");
                return;
            }
            this.submitted = true;
            this.score = 0;
            for (Question q : examination.getQuestions()) {
                String ans = responses.get(q.getId());
                if (ans != null && q.checkAnswer(ans)) {
                    score++;
                }
            }
            System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
            System.out.println("Result for '" + examination.getTitle() + "' attempt: " +
                    score + "/" + examination.getQuestions().size() + " correct");
        }
    }

    public static void main(String[] args) {
        Examination exam = new Examination("Math Quiz");
        exam.addQuestion(new MultipleChoiceQuestion("1", "2 + 2 = ?", "A"));
        exam.addQuestion(new MultipleChoiceQuestion("2", "5 * 3 = ?", "B"));

        Attempt attempt = new Attempt(exam);
        attempt.answerQuestion("1", "A");
        attempt.answerQuestion("2", "C");
        attempt.submit();
    }
}
