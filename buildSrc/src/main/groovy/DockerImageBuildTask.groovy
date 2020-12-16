import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path

class DockerImageBuildTask extends DefaultTask {

    private static final String DOCKER_REPOSITORY = "kolegran"
    private static final String JAR_DIR = "libs"
    private static final String DOCKER_BUILD_SCRIPT = "build_docker_image.sh"
    private static final String DOCKERFILE = "Dockerfile"
    private static final String SEPARATOR = File.separator
    private final Path buildDirPath = getProject().getBuildDir().toPath()

    @TaskAction
    void run() {
        try {
            modifyDockerBuildScript(DOCKER_BUILD_SCRIPT)
            modifyDockerfile(DOCKERFILE)

            final File file = new File(buildDirPath.toString() + SEPARATOR + DOCKER_BUILD_SCRIPT)
            (file.getText()).execute()
        } catch (IOException e) {
            throw new HandleFileException(e)
        }
    }

    private String modifyDockerBuildScript(String resourceName) {
        final InputStream inputStream = getClass().getResourceAsStream(resourceName)
        final String modifiedScript = inputStream.getText()
            .replace("{{DOCKER_REPOSITORY}}", DOCKER_REPOSITORY)
            .replace("{{UPLOAD_PROJECT_NAME}}", getProject().getName())
            .replace("{{BUILD_DIR}}", buildDirPath.toString())
        createModifiedFile(resourceName, modifiedScript.getBytes())
    }

    private void modifyDockerfile(String resourceName) {
        final InputStream inputStream = getClass().getResourceAsStream(resourceName)
        final String replacePathToJar = inputStream.getText().replace("\${PATH_TO_JAR}", JAR_DIR)

        final String pathToJar = buildDirPath.toString() + SEPARATOR + JAR_DIR
        final File[] files = new File(pathToJar).listFiles()
        final String jarName = files[0].getName()
        final String replaceJarName = replacePathToJar.replace("\${JAR}", jarName)
        createModifiedFile(resourceName, replaceJarName.getBytes())
    }

    private void createModifiedFile(String resourceName, byte[] content) {
        Files.write(buildDirPath.resolve(resourceName), content)
    }

    private static final class HandleFileException extends RuntimeException {

        HandleFileException(Exception exception) {
            super(exception)
        }
    }
}
