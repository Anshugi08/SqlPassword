<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html">
<h:head>
    <title>Doctor Dashboard</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>
</h:head>

<h:body>
    <h:form>
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
            <a class="navbar-brand" href="#">ClinicCare</a>
            <button class="navbar-toggler" type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link" href="#">Dashboard</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Patients</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Appointments</a></li>
                </ul>
                <span class="navbar-text">Dr. Alex Morgan</span>
            </div>
        </nav>

        <div class="container-fluid">
            <!-- Top Cards -->
            <div class="row mb-4">
                <div class="col-md-4">
                    <div class="card text-white bg-primary h-100">
                        <div class="card-header">Today's Appointments</div>
                        <div class="card-body">
                            <h5 class="card-title">8</h5>
                            <p class="card-text">View all appointments</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-success h-100">
                        <div class="card-header">Total Patients</div>
                        <div class="card-body">
                            <h5 class="card-title">1,200</h5>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-white bg-info h-100">
                        <div class="card-header">New Messages</div>
                        <div class="card-body">
                            <h5 class="card-title">5</h5>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <!-- Appointments Table -->
                <div class="col-lg-8 mb-4">
                    <div class="card h-100">
                        <div class="card-header">Appointments Today</div>
                        <div class="card-body p-0">
                            <table class="table mb-0">
                                <thead class="table-light">
                                    <tr>
                                        <th>Patient</th>
                                        <th>Time</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Jane Doe</td>
                                        <td>09:00 AM</td>
                                        <td><span class="badge bg-success">Confirmed</span></td>
                                    </tr>
                                    <tr>
                                        <td>John Smith</td>
                                        <td>10:30 AM</td>
                                        <td><span class="badge bg-warning text-dark">Pending</span></td>
                                    </tr>
                                    <tr>
                                        <td>Mary Johnson</td>
                                        <td>11:15 AM</td>
                                        <td><span class="badge bg-secondary">Completed</span></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Quick Actions & Chart -->
                <div class="col-lg-4">
                    <div class="card mb-4">
                        <div class="card-header">Quick Actions</div>
                        <div class="card-body">
                            <button class="btn btn-outline-primary w-100 mb-2">Add Note</button>
                            <button class="btn btn-outline-secondary w-100 mb-2">Send Message</button>
                            <button class="btn btn-outline-success w-100">New Appointment</button>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">Patient Conditions</div>
                        <div class="card-body text-center">
                            <canvas id="conditionChart" width="200" height="200"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            const ctx = document.getElementById('conditionChart').getContext('2d');
            new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['Diabetes', 'Hypertension', 'Others'],
                    datasets: [{
                        data: [25, 40, 35],
                        backgroundColor: ['#0d6efd', '#198754', '#0dcaf0']
                    }]
                }
            });
        </script>
    </h:form>
</h:body>
</html>
