import org.gradle.api.Plugin
import org.gradle.api.Project

class SampleApplicationImageBuilder implements Plugin<Project> {

    @Override
    void apply(Project project) {
        final String script = "cmd /c D:/kolegran/" + project.rootProject.name + "/build_and_push_docker_image.sh"
        script.execute()
    }
}
