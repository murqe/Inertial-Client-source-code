// extra changes by aidene

#ifdef GL_ES
precision mediump float;
#endif

#define PI 3.14

uniform float time;
uniform vec2 resolution;

void main( void ) {

    vec3 normalized_dot = normalize(vec3((gl_FragCoord.xy - resolution.xy * .45) / resolution.x, .64));
    vec3 sized=vec3(7);
    vec3 foreground=vec3(114);
    vec3 fracted_normalized_dot=vec3(8);
    vec3 camera=vec3(0);
    vec3 background=normalized_dot;
    vec3 light=vec3(1.0,2.3,0.0);
    camera.y = 1.3*cos((camera.x=0.100)*(camera.z=time * 10.0));
    camera.x -= sin(time) + 6.0;

    for( float depth=.10; depth<75.; depth+=.1 ) {
        fracted_normalized_dot = fract(foreground = camera += normalized_dot*depth*.099);
        sized = floor( foreground )*.24;
        if( cos(sized.z) + sin(sized.x) > ++sized.y ) {
            background = (fracted_normalized_dot.y-.04*cos((foreground.x+foreground.z)*40.)>.5?light:fracted_normalized_dot.x*light.yxz) / depth;
            break;
        }
    }
    gl_FragColor = vec4(background,30.8);


}