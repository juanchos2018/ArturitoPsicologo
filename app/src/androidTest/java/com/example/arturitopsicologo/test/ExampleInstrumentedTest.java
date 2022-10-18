package com.example.arturitopsicologo.test;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.arturitopsicologo.Test.UsuarioTest;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private UsuarioTest usuarioTest;

    @Before
    public void  setUp(){
        usuarioTest = new UsuarioTest();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.arturitopsicologo", appContext.getPackageName());
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

    @Test
    public  void AgregarFecha(){
        String user_id="5pjnY3QWwTeOOMEWXpueeJPgQHm1";
        String fecha ="04-10-2022";
        assertEquals("ok",usuarioTest.AgregarFecha(null,user_id));
    }
    @Test
    public  void AgregarHora(){
        String user_id="5pjnY3QWwTeOOMEWXpueeJPgQHm1";
        String fecha ="04-10-2022";
        assertEquals("ok",usuarioTest.AgregarHora(null,"-ND-q6ylq2gwDdQh4sNb","09:30 a.m.","10:30 a.m."));
    }

    @Test
    public  void EditarHora(){
        String user_id="5pjnY3QWwTeOOMEWXpueeJPgQHm1";
        String hora_id ="-ND-ya5eRBRaWviqnsVq";
        String fecha_id="-ND-q6ylq2gwDdQh4sNb";
        assertEquals("ok",usuarioTest.EditarHora(user_id,hora_id,fecha_id,"09:30 a.m.","10:30 a.m."));
    }

}