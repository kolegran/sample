import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path

class DockerImageBuildTask extends DefaultTask {
    private static final String DOCKER_BUILD = "build_docker_image.sh"
    private static final String DOCKERFILE = "Dockerfile"

    @TaskAction
    void run() {
        final Path buildDirPath = getProject().getBuildDir().toPath()
        print(buildDirPath)
        try {
            Files.write(buildDirPath.resolve(DOCKER_BUILD), getFileContent(DOCKER_BUILD))
            Files.write(buildDirPath.resolve(DOCKERFILE), getFileContent(DOCKERFILE))
        } catch (IOException e) {
            throw new HandleFileException(e);
        }
    }

    private byte[] getFileContent(String resourceName) {
        final InputStream is = getClass().getResourceAsStream(resourceName)
        return is.getBytes()
    }

    private static final class HandleFileException extends RuntimeException {

        HandleFileException(Exception exception) {
            super(exception)
        }
    }
}
