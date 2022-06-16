package com.example.tipapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipapp.components.CreateTextField
import com.example.tipapp.ui.theme.TipAppTheme
import com.example.tipapp.utils.calculateTotalAmountPerPerson
import com.example.tipapp.utils.calculateTotalTip
import com.example.tipapp.widgets.RoundIconButton

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AllContent()
                }
            }
        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun AllContent(){
    Column(Modifier.fillMaxSize()) {
        val totalPerPerson = remember {
            mutableStateOf(0.0)
        }

        BillForm()
    }
}

@Composable
fun CreatePerPersonCount(totalPerPerson: Double=133.0) {

   Card(
       modifier = Modifier
           .fillMaxWidth()
           .height(150.dp)
           .padding(12.dp),
       elevation = 6.dp,
       shape = RoundedCornerShape(corner = CornerSize(10.dp)),
       backgroundColor = Color(0xFFE9D7F7)
   ) {
       
       Column(
           Modifier
               .fillMaxWidth(),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           
           Text(
               text = "Total Per Person",
               style = MaterialTheme.typography.h6,
               fontWeight = FontWeight.Bold,
           )
           
           Spacer(modifier = Modifier.height(5.dp))
           var total="%.2f".format(totalPerPerson)
           Text(
               text = "$$total",
               style = MaterialTheme.typography.h5,
               fontWeight = FontWeight.Bold
           )
       }

   }
}


@ExperimentalComposeUiApi
@Composable
fun BillForm(
    onValueChange : (String) -> Unit = { }
){
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState= remember(totalBillState.value){
        totalBillState.value.trim().isNotEmpty()
    }

    val personValueState= remember {
        mutableStateOf(1)
    }
    val sliperPositionState= remember {
        mutableStateOf(0f)
    }

    val tipAmountState= remember {
        mutableStateOf(0.0)
    }

    val totalPerPersonBillState= remember {
        mutableStateOf(0.0)
    }

    val tipPercentages= (sliperPositionState.value * 100).toInt()

    val keyboardController=LocalSoftwareKeyboardController.current

    CreatePerPersonCount(totalPerPerson = totalPerPersonBillState.value)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(1.dp,color = Color.LightGray),
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            CreateTextField(
                valueState = totalBillState,
                labelId = "Enter Bill Amount",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValueChange(totalBillState.value.trim())
                    keyboardController?.hide()
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (validState){
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split",
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        modifier= Modifier
                            .weight(0.1f)
                            .align(alignment = Alignment.CenterVertically)
                    )

                    Row(
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(horizontal = 3.dp),

                    ) {

                        RoundIconButton(
                            imageVector = Icons.Filled.Remove,
                            onClick = {
                                if (personValueState.value>1)
                                personValueState.value-=1
                                totalPerPersonBillState.value=calculateTotalAmountPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentages,
                                    personValueState.value
                                )
                            }
                        )

                        Text(
                            text = personValueState.value.toString(),
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground,
                            modifier= Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )

                        RoundIconButton(
                            imageVector = Icons.Filled.Add,
                            onClick = {
                                personValueState.value+=1
                                totalPerPersonBillState.value=calculateTotalAmountPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentages,
                                    personValueState.value
                                )
                            }
                        )
                    }
                }



                // Tip Row
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                ) {
                    Text(
                        text = "Tip",
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        modifier=Modifier.weight(0.1f)
                    )

                   // Spacer(modifier = Modifier.width(120.dp))

                    Text(
                        text = "$${tipAmountState.value}",
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground,
                        modifier= Modifier
                            .padding(horizontal = 10.dp)
                            .weight(0.1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$tipPercentages %",
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )

                    Slider(
                        modifier=Modifier.padding(start = 10.dp,end = 10.dp),
                        value = sliperPositionState.value,
                        onValueChange = {
                            sliperPositionState.value=it
                            tipAmountState.value=calculateTotalTip(
                                totalBillState.value.toDouble(),
                                tipPercentages
                            )

                            totalPerPersonBillState.value=calculateTotalAmountPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage= tipPercentages,
                                persons=personValueState.value
                            )
                        },
                        steps = 5,
                    )
                }


            }else{
                totalPerPersonBillState.value=0.0
                Box() {
                    
                }
            }
        }

    }
}




@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipAppTheme {
        AllContent()
    }
}