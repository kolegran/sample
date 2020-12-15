import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path

class DockerImageBuildTask extends DefaultTask {

    private static final String DOCKER_REPOSITORY = "kolegran"
    private static final String DOCKER_BUILD_SCRIPT = "build_docker_image.sh"
    private static final String DOCKERFILE = "Dockerfile"

    @TaskAction
    void run() {
        try {
            modifyDockerBuildScript(DOCKER_BUILD_SCRIPT);
        } catch (IOException e) {
            throw new HandleFileException(e)
        }
    }

    private void modifyDockerBuildScript(String resourceName) {
        final InputStream inputStream = getClass().getResourceAsStream(resourceName)
        final String replaceDockerRepository = inputStream.getText().replace("{{DOCKER_REPOSITORY}}", DOCKER_REPOSITORY)
        final String replaceUploadProjectName = replaceDockerRepository.replace("{{UPLOAD_PROJECT_NAME}}", getProject().getName())
        createModifiedFile(DOCKER_BUILD_SCRIPT, replaceUploadProjectName.getBytes())
    }

    private void createModifiedFile(String resourceName, byte[] content) {
        final Path buildDirPath = getProject().getBuildDir().toPath()
        Files.write(buildDirPath.resolve(resourceName), content)
    }

    private static final class HandleFileException extends RuntimeException {

        HandleFileException(Exception exception) {
            super(exception)
        }
    }
}
