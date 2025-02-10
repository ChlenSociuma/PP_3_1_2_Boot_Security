$(document).ready(function() {
    $('.edit-btn').on('click', function() {
        var row = $(this).closest('tr');
        var id = row.find('td:eq(0)').text();
        var firstName = row.find('td:eq(1)').text();
        var lastName = row.find('td:eq(2)').text();
        var age = row.find('td:eq(3)').text();
        var username = row.find('.username').text();

        $('#editUserId').val(id);
        $('#hiddenEditUserId').val(id);
        $('#editUsername').val(username);
        $('#editFirstName').val(firstName);
        $('#editLastName').val(lastName);
        $('#editAge').val(age);
    });

    $('.del-button').on('click', function() {
        var row = $(this).closest('tr');
        var id = row.find('td:eq(0)').text();
        var firstName = row.find('td:eq(1)').text();
        var lastName = row.find('td:eq(2)').text();
        var age = row.find('td:eq(3)').text();
        var username = row.find('.username').text();

        $('#DelUserId').val(id);
        $('#hiddenDelUserId2').val(id);
        $('#delUsername').val(username);
        $('#delFirstName').val(firstName);
        $('#delLastName').val(lastName);
        $('#delAge').val(age);
    });
});