package tw.edu.pu.gm.s1101761.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import tw.edu.pu.gm.s1101761.compose.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("多指觸控Compose實例")
                }
            }
        }
    }
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Greeting(name: String) {
    var X = remember { mutableStateListOf(0f) }
    var Y = remember { mutableStateListOf(0f) }
    var Fingers = remember { mutableStateOf (0) }
    val love = ImageBitmap.imageResource(id = R.drawable.love)
    var PaintColor:Color
    var colors = arrayListOf(
        ColorRed, ColorOrange, ColorYellow, ColorGreen,
        ColorBlue, ColorIndigo, ColorPurple
    )

    Box(
        modifier = Modifier.fillMaxSize().pointerInteropFilter {
                event ->
            Fingers.value = event.getPointerCount()
            X.clear()
            Y.clear()
            for (i in 0..Fingers.value - 1) {
                X.add( event.getX(i))
                Y.add (event.getY(i))
            }
                true
            }

    ){
        Canvas(modifier = Modifier){
            for (i in 0..Fingers.value - 1) {
                PaintColor = colors[i % 7]
                drawCircle(PaintColor, 100f, Offset(X[i], Y[i]))
                //drawCircle(Color.Yellow, 100f, Offset(X[i], Y[i]))
                //drawImage(love, Offset(X[i]-love.width/2,Y[i]-love.height/2))
            }
        }
    }


    Column() {
        Row() {
            Text(
                text = "$name!",
                color = Color.Cyan,
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.kai))
            )
            Image(
                painter = painterResource(id = R.drawable.hand),
                contentDescription = "手部圖片",
                alpha = 0.7f,
                modifier = Modifier.clip(CircleShape).background(Color.Blue)
            )
        }
        Text(text = "作者:楊子青")
        Box(
            Modifier.fillMaxSize() ,
            contentAlignment = Alignment.Center
        ){
            var count = remember { mutableStateOf(0) }
            Text(text = count.value.toString(),
                 fontSize = 50.sp,
                 modifier = Modifier.clickable { count.value += 1 }
            )
            }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Greeting("s1101761")
    }
}