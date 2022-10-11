package com.example.arturitopsicologo.Test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class UsuarioTestTest extends TestCase {

    private  UsuarioTest usuarioTest;


    @Before
    public void  setUp(){
        usuarioTest = new UsuarioTest();
    }


    @Test
    public void testLogin() {
        assertEquals("ok",usuarioTest.login("usario@gmaiul.com","123456"));

    }
    @Test
    public  void EditarPerfil(){
        String user_id="5pjnY3QWwTeOOMEWXpueeJPgQHm1";
        assertEquals("ok",usuarioTest.editarPerfil(user_id,null,"Guitierrez","970780836","mi informacion","mi formacion","mi descripcion"));
    }

}