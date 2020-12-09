import org.gradle.api.Plugin
import org.gradle.api.Project

class SampleApplicationImageBuilder implements Plugin<Project> {

    @Override
    void apply(Project project) {
        File projectDirectory = project.rootProject.getProjectDir()
        String script = projectDirectory.getAbsolutePath() + "/build_and_push_docker_image.sh"
        script.execute()
    }
}
