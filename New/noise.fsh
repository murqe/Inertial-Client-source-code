#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

//Made By Deleteboys

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;


vec3 hsv2rgb(  vec3 c )
{
    vec3 rgb = clamp( abs(mod(c.x*6.0+vec3(0.0,4.0,2.0),6.0)-3.0)-1.0, 0.0, 1.0 );
    rgb = rgb*rgb*(3.0-2.0*rgb);
    return c.z * mix( vec3(1.0), rgb, c.y);
}

void main( void ) {

    vec2 position = ( gl_FragCoord.xy / resolution.xy );

    vec3 color = vec3(0.1, 0.1, 0.1);

    color = hsv2rgb(vec3(time * 0.3 + position.x - position.y,0.5,1.0));

    gl_FragColor = vec4(color,1.0);
}