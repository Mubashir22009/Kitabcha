package app.kitabcha.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import app.kitabcha.navigation.Routes

@Composable fun LibraryScreen(
    navController: NavController,
    userId: Int,
) {
    val viewModel = hiltViewModel<LibraryScreenViewModel>()

    Content(viewModel, navController, userId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    libraryViewModel: LibraryScreenViewModel,
    navController: NavController,
    userId: Int,
) {
    val allCategores by libraryViewModel.userCategories.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        libraryViewModel.getCategoryIdUsingUserId(userId)
    }

    var isPopupVisible by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors =
                    TopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = MaterialTheme.colorScheme.surfaceVariant,
                        navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                        scrolledContainerColor = MaterialTheme.colorScheme.secondary,
                    ),
                title = {
                    Text("Categories")
                },
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = 15.dp),
                ) {
                    Button(
                        onClick = {
                            navController.navigate(route = "${Routes.sourceScreen}/$userId")
                        },
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Text("Source Screen")
                    }
                    Button(
                        onClick = { isPopupVisible = true },
                        modifier = Modifier,
                        shape = MaterialTheme.shapes.large, // Adjust shape as needed
                    ) {
                        Text(
                            text = "Add Category",
                        )
                    }
                    Button(
                        onClick = { },
                        modifier = Modifier,
                        shape = MaterialTheme.shapes.medium, // Adjust shape as needed
                    ) {
                        Text(
                            text = "Delete User",
                        )
                    }
                }
            }
        },
    ) { pad ->
        Box(
            modifier =
                Modifier
                    .padding(bottom = 80.dp)
                    .padding(top = pad.calculateTopPadding())
                    .fillMaxSize(),
        ) {
            if (allCategores.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        allCategores,
                    ) { cat ->
                        Row(
                            Modifier
                                .padding(15.dp)
                                .fillMaxSize(),
                        ) {
                            Text(
                                text = cat.catTitle,
                                modifier =
                                    Modifier
                                        .clickable {
                                            navController.navigate("${Routes.mangaLibraryScreen}/$userId/${cat.catID}")
                                        }
                                        .fillMaxWidth(),
                            )
                        }
                    }
                }
            } else {
                Text(
                    modifier =
                        Modifier
                            .align(Alignment.Center),
                    text = "empty",
                )
            }
        }

        if (isPopupVisible) {
            PopupTextField(
                text = text,
                onTextChanged = { text = it },
                onDismiss = { isPopupVisible = false },
                libraryViewModel = libraryViewModel,
                userId = userId,
                clearText = { text = "" },
            )
        }
    }
}

@Composable
fun PopupTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    libraryViewModel: LibraryScreenViewModel,
    userId: Int,
    clearText: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Category Name") },
        text = {
            TextField(
                value = text,
                onValueChange = { onTextChanged(it) },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        confirmButton = {
            Button(onClick = {
                if (text.isNotEmpty()) {
                    libraryViewModel.insertCategory(text, userId)
                    clearText()
                }

                onDismiss()
            }) {
                Text("ok")
            }
        },
    )
}
