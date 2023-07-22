#version 330
uniform vec3 transl;
in vec3 pos;
void main() {
    gl_Position = vec4(pos.x+transl.x, pos.y+transl.y, pos.z+transl.z, 1.0);
}
