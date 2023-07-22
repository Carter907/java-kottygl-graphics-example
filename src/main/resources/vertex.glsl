#version 330
uniform vec3 transl;
in vec3 pos;
in vec3 vert_Color; // in through an attribute
out vec3 color; // out to the fragment shader
void main() {
    gl_Position = vec4(pos.x+transl.x, pos.y+transl.y, pos.z+transl.z, 1.0);
    color = vert_Color; // passing the variable through the pipeline.
}
