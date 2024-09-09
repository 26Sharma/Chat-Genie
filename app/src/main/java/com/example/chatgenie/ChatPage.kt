package com.example.chatgenie

import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatgenie.ui.theme.ColorModelMessage
import com.example.chatgenie.ui.theme.ColorUserMessage
import com.example.chatgenie.ui.theme.Purple80

@Composable
fun ChatPage(modifier: Modifier= Modifier,viewModel: ChatViewModel) {
    Column(modifier = modifier) {
          AppHeader()
        MessageList(modifier= Modifier.weight(1f),messageList = viewModel.messageList)
        MessageInput(onMessageSent = {
           viewModel.sendMessage(it)
        })
    }
}
@Composable
fun MessageList(modifier: Modifier= Modifier,messageList:List<MessageModel>) {
   if (messageList.isEmpty()){

        Column(modifier=modifier.padding(horizontal = 112.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
            Icon(modifier= Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription ="Icon" ,
                tint = Purple80
                )
            Text(text = "Ask me anything", fontSize = 22.sp)

        }

 }


  else {
       LazyColumn(modifier = modifier, reverseLayout = true) {
           items(messageList.reversed()) {
               MessageRow(messageModel = it)
           }
       }
   }

}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel= messageModel.role=="model"
    
    Row (verticalAlignment = Alignment.CenterVertically){
        Box(modifier = Modifier.fillMaxWidth()){
            Box(modifier = Modifier
                .align(
                    if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                )
                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .clip(RoundedCornerShape(50f))
                .background(if (isModel) ColorModelMessage else ColorUserMessage)
                .padding(16.dp)

            ){
                SelectionContainer {
                    Text(text = messageModel.message,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White)
                }

            }

        }

    }
}

@Composable
fun MessageInput(onMessageSent :(String)-> Unit) {
    var message by remember{
        mutableStateOf("")
    }

    Row(modifier=Modifier.padding( end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {
        OutlinedTextField(modifier = Modifier.weight(1f),
                          value = message,
                         onValueChange ={
                          message=it
        } )
        IconButton(onClick = { /*TODO*/
            if (message.isNotEmpty()){
                onMessageSent(message)
                message = ""
            }

        }) {
            Icon(imageVector = Icons.Default.Send, contentDescription ="Send" )
        }
        
    }
}


@Composable
fun AppHeader() {

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)){
        Text(modifier=Modifier.padding(16.dp),
            text = "Chat Genie",
            fontSize = 22.sp,
            color = Color.White
            )

    }
}