#version 330
out vec4 frag_Color;
in vec3 color;
void main() {
    frag_Color = vec4 (color.r, color.g, color.b, 0);
}
