package org.example;

import static org.lwjgl.opengl.GL33.*;
import org.carte.kottygraphics.core.math.Vector;

import org.carte.kottygraphics.core.*;
import org.carte.kottygraphics.util.ProgramUtils;
import org.carte.kottygraphics.util.ShaderUtils;



import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

public class RedTriangleApp extends GlApp {

    private Uniform<Vector> transl;
    private KeyHandler input;
    public final int speed = 2;

    @Override
    public void onCreate() {

        int programRef = ProgramUtils.INSTANCE.initProgram(
                ShaderUtils.INSTANCE.getShaderCode("/vertex.glsl"),
                ShaderUtils.INSTANCE.getShaderCode("/fragment.glsl")
        );

        setProgramRef(programRef);

        input = new KeyHandler(getProgramRef());

        int vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);

        float size = .25f;

        float[] vertices = {
                -size, -size, 0f,
                size, -size, 0f,
                0f, size, 0f
        };
        var vertAttribute = new Attribute(
                GlDataType.VEC3,
                vertices
        );
        vertAttribute.associateVariable(getProgramRef(), "pos");

        float[] vertColors = {
                1f, 0f, 0f,
                0f, 1f, 0f,
                0f, 0f, 1f,
        };

        var vertColorAttribute = new Attribute(
                GlDataType.VEC3,
                vertColors
        );
        vertColorAttribute.associateVariable(getProgramRef(), "vert_Color");

        transl = new Uniform<>(
                GlDataType.VEC3,
                new Vector(0.0,0.0,0.0)
        );
        transl.locateVariable(programRef, "transl");

    }

    @Override
    public void onKeyEvent(long window, int key, int action) {
        input.update(key, action);
    }

    @Override
    public void onWindowResized(int width, int height) {

        glViewport(0, 0, width, height);
    }

    @Override
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glUseProgram(getProgramRef());

        if (input.isKeyDown(GLFW_KEY_W))
            transl.getData().getValues()[1] += speed * getDeltaTime();
        if (input.isKeyDown(GLFW_KEY_S))
            transl.getData().getValues()[1] -= speed * getDeltaTime();
        if (input.isKeyDown(GLFW_KEY_A))
            transl.getData().getValues()[0] -= speed * getDeltaTime();
        if (input.isKeyDown(GLFW_KEY_D))
            transl.getData().getValues()[0] += speed * getDeltaTime();


        transl.uploadData();

        glDrawArrays(GL_TRIANGLES, 0, 3);

    }

    @Override
    public void shutdown() {
        glfwFreeCallbacks(getWindow());
        glfwDestroyWindow(getWindow());
        try (var callback = glfwSetErrorCallback(null)) {
            assert callback != null : "glfw Error Callback was null";
            callback.free();
        }
        glfwTerminate();
    }
}
