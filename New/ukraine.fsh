#extension GL_OES_standard_derivatives : enable

precision highp float;

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

void main( void ) {

    vec3 res;
    if(gl_FragCoord.y > resolution.y*.5)
    res = vec3(.1,.3,1.0);
    else
    res = vec3(1.0,1.0,0.0);

    gl_FragColor = vec4(res, 1.0 );

}