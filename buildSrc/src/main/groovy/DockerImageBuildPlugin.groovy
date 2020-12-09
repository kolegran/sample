import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerImageBuildPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.getTasks().create("buildDockerImage", DockerImageBuildTask.class)
    }
}
