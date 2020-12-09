import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerBuildImagePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.getTasks().create("buildDockerImage", DockerBuildImageTask.class)
    }
}
