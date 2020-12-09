import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DockerImageBuildTask extends DefaultTask {

    @TaskAction
    void run() {
        final InputStream scriptContent = getClass().getResourceAsStream("build_docker_image.sh")
        (scriptContent.text).execute()
    }
}
