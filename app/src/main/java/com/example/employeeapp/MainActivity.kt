package com.example.employeeapp

import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import com.example.employeeapp.ui.theme.EmployeeAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmployeeAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = R.drawable.greendzine_logo),
                                        contentDescription = "Greendzine Logo",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(end = 8.dp)
                                    )
                                    Text(
                                        text = "Greendzine Technologies",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32))
                        )
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // 🔽 Your UI function here, like:
                        val viewModel: MainViewModel by viewModels()

                        UserListScreen(viewModel = viewModel)

                    }
                }
            }
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun UserListScreen(viewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredUsers = viewModel.userList.filter {
        it.firstName.contains(searchQuery.text, ignoreCase = true)
    }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.refreshUsers() },
        modifier = Modifier.padding(16.dp)
    ) {
        Column {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by first name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            if (viewModel.isLoading) {
                CircularProgressIndicator()
            } else if (viewModel.errorMessage.isNotEmpty()) {
                Text(text = viewModel.errorMessage, color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredUsers) { user ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(user.avatar),
                                    contentDescription = "${user.firstName}'s Avatar",
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                )

                                Column {
                                    Text(
                                        text = "${user.firstName} ${user.lastName}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = user.email,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}