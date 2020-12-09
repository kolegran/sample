import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DockerBuildImageTask extends DefaultTask {

    @TaskAction
    void run() {
        File projectDirectory = project.rootProject.getProjectDir()
        String script = projectDirectory.getAbsolutePath() + "/build_and_push_docker_image.sh"
        script.execute()
    }
}
