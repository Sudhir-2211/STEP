package Week8;

import java.util.*;

public class Problem1 {

    // Status enum representing Hackathon lifecycle
    public enum HackathonState {
        OPEN, JUDGING, PUBLISHED
    }

    // Student model
    public static class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // ScoringRule interface for polymorphic score calculation
    public interface ScoringRule {
        String getTrackName();
        double calculateFinalScore(double idea, double execution, double presentation);
    }

    public static class InnovationTrack implements ScoringRule {
        @Override
        public String getTrackName() {
            return "Innovation track";
        }

        @Override
        public double calculateFinalScore(double idea, double execution, double presentation) {
            return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
        }
    }

    public static class OpenTrack implements ScoringRule {
        @Override
        public String getTrackName() {
            return "Open track";
        }

        @Override
        public double calculateFinalScore(double idea, double execution, double presentation) {
            return (idea + execution + presentation) / 3.0;
        }
    }

    // Score model
    public static class Score {
        private double idea;
        private double execution;
        private double presentation;
        private double finalScore;

        public Score(double idea, double execution, double presentation, double finalScore) {
            this.idea = idea;
            this.execution = execution;
            this.presentation = presentation;
            this.finalScore = finalScore;
        }

        public double getFinalScore() {
            return finalScore;
        }
    }

    // Project model
    public static class Project {
        private String name;
        private Score score;

        public Project(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Score getScore() {
            return score;
        }

        public void setScore(Score score) {
            this.score = score;
        }
    }

    // Team model
    public static class Team {
        private String name;
        private List<Student> members;
        private ScoringRule track;
        private Project project;

        public Team(String name, List<Student> members, ScoringRule track) {
            this.name = name;
            this.members = new ArrayList<>(members);
            this.track = track;
        }

        public String getName() {
            return name;
        }

        public List<Student> getMembers() {
            return members;
        }

        public ScoringRule getTrack() {
            return track;
        }

        public Project getProject() {
            return project;
        }

        public void setProject(Project project) {
            this.project = project;
        }
    }

    // Hackathon management system
    public static class Hackathon {
        private String name;
        private HackathonState state = HackathonState.OPEN;
        private Map<String, Team> teams = new HashMap<>();
        private Set<String> registeredStudents = new HashSet<>();
        private Map<String, Project> projects = new HashMap<>();

        public Hackathon(String name) {
            this.name = name;
        }

        public boolean registerTeam(String teamName, List<Student> members, ScoringRule track) {
            if (members == null || members.size() < 2 || members.size() > 4) {
                System.out.println("Registration failed: A team must have 2 to 4 members.");
                return false;
            }

            // Check if any student already registered in another team
            for (Student s : members) {
                if (registeredStudents.contains(s.getName())) {
                    System.out.println("Registration failed: Student " + s.getName() + " is already in a team.");
                    return false;
                }
            }

            Team team = new Team(teamName, members, track);
            teams.put(teamName, team);
            for (Student s : members) {
                registeredStudents.add(s.getName());
            }

            System.out.println("Team " + teamName + " registered (" + members.size() + " members, " + track.getTrackName() + ").");
            return true;
        }

        public boolean submitProject(String teamName, String projectName) {
            Team team = teams.get(teamName);
            if (team == null) {
                System.out.println("Submission failed: Team not found.");
                return false;
            }
            if (team.getProject() != null) {
                System.out.println("Submission failed: Team has already submitted a project.");
                return false;
            }

            Project project = new Project(projectName);
            team.setProject(project);
            projects.put(projectName, project);
            System.out.println("Project '" + projectName + "' submitted by " + teamName + ".");
            return true;
        }

        public boolean scoreProject(String projectName, double idea, double execution, double presentation) {
            if (state == HackathonState.PUBLISHED) {
                System.out.println("Rescore rejected: Results have already been published.");
                return false;
            }

            Project project = projects.get(projectName);
            if (project == null) {
                System.out.println("Scoring failed: Project not found.");
                return false;
            }

            // Find team owning this project to get scoring track rule
            Team owningTeam = null;
            for (Team t : teams.values()) {
                if (t.getProject() != null && t.getProject().getName().equals(projectName)) {
                    owningTeam = t;
                    break;
                }
            }

            if (owningTeam == null) {
                System.out.println("Scoring failed: No owning team found for project.");
                return false;
            }

            state = HackathonState.JUDGING;
            double finalScore = owningTeam.getTrack().calculateFinalScore(idea, execution, presentation);
            project.setScore(new Score(idea, execution, presentation, finalScore));
            System.out.printf(Locale.US, "Score recorded for '%s'. Final score: %.2f.\n", projectName, finalScore);
            return true;
        }

        public void publishResults() {
            this.state = HackathonState.PUBLISHED;
            System.out.println("Results published.");
        }
    }

    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");

        // 1. Team 'ByteBusters' registers with 3 members in Innovation track
        List<Student> byteBustersMembers = Arrays.asList(
                new Student("Asha"), new Student("Ravi"), new Student("Neha")
        );
        hackathon.registerTeam("ByteBusters", byteBustersMembers, new InnovationTrack());

        // 2. Team 'SoloCoder' registers with 1 member in Open track
        List<Student> soloMembers = Collections.singletonList(new Student("Kiran"));
        hackathon.registerTeam("SoloCoder", soloMembers, new OpenTrack());

        // 3. ByteBusters submits project 'SmartAttend'
        hackathon.submitProject("ByteBusters", "SmartAttend");

        // 4. Judge scores 'SmartAttend': idea 8, execution 7, presentation 9
        hackathon.scoreProject("SmartAttend", 8, 7, 9);

        // 5. Organiser publishes results
        hackathon.publishResults();

        // 6. Judge attempts to change the idea score of 'SmartAttend' to 10
        hackathon.scoreProject("SmartAttend", 10, 7, 9);
    }
}
