<script>
    import { onMount } from 'svelte';
    import axios from 'axios';

    const backendUrl = 'http://127.0.0.1:8080/';
    const AXIOS = axios.create({
        baseURL: backendUrl,
        headers: { 'Access-Control-Allow-Origin': 'http://localhost:5173/' }
    });


    let errorType;
    /**
     * An array of items.
     * @type {string}
     */
    let selectedCourse = "";
    /**
     * An array of items.
     * @type {string}
     */
    let selectedType = "";
    /**
     * An array of items.
     * @type {string}
     */
    let startDate = "";
    /**
     * An array of items.
     * @type {string}
     */
    let endDate = "";
    /**
     * An array of items.
     * @type {number}
     */
    let priceNew = 0;
    /**
     * An array of items.
     * @type {number}
     */
    let floorNumberNew = 0;
    /**
     * An array of items.
     * @type {number}
     */
    let roomNumberNew = 0;
    /**
     * An array of items.
     * @type {string}
     */
    let startDateNew = "";
    /**
     * An array of items.
     * @type {string}
     */
    let endDateNew = "";
    /**
     * An array of items.
     * @type {string[]}
     */
    let startTime = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let endTime = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let sessionDate = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let sessions = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let daysOffered = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let daysOfferedNew = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let taughtCourses = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let taughtCoursesStartDate = [];
    /**
     * An array of items.
     * @type {string[]}
     */
    let taughtCoursesEndDate = [];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesSessionsStart= [[]];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesSessionsEnd = [[]];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let  taughtCoursesSessionsDate = [[]];
    /**
     * An array of items.
     * @type {string[][]}
     */
    let taughtCoursesDaysOffered= [[]];
    /**
     * An array of items.
     * @type {string[]}
     */
    let approvedTypes = [];
    /**
     * An array of items.
     * @type {number[]}
     */
    let approvedTypesID = [];
    /**
     * An array of items.
     * @type {number[]}
     */
    let taughtCoursesID = [];

    let monday = daysOffered.includes('MONDAY');
    let tuesday = daysOffered.includes('TUESDAY');
    let wednesday = daysOffered.includes('WEDNESDAY');
    let thursday = daysOffered.includes('THURSDAY');
    let friday = daysOffered.includes('FRIDAY');
    let saturday = daysOffered.includes('SATURDAY');
    let sunday = daysOffered.includes('SUNDAY');

    let showUpdateContentInfo = false;

    function toggleUpdateContentInfo() {
        showUpdateContentInfo = !showUpdateContentInfo;
    }

    /**
     * Handles updating the daysOffered list based on checkbox selection.
     * @param {string} day - The day being updated.
     * @returns {void}
     */
    function updateDaysOffered(day) {
        if (daysOfferedNew.includes(day)) {
            // Remove the day if it's already in the list
            daysOfferedNew = daysOffered.filter(d => d !== day);
        } else {
            // Add the day if it's not in the list
            daysOfferedNew.push(day);
        }
    }

    let showUpdateContentSessions = false;

    function toggleUpdateContentSessions() {
        showUpdateContentSessions = !showUpdateContentSessions;
    }


    /**
     * Handles the check button click event.
     * @param {number} courseTypeId - The type of course to be approved.
     * @returns {Promise<string>} - The ID of the clicked item.
     */
    async function getCourseName(courseTypeId) {
        try {
            const response = await AXIOS.get(`/courseTypes/get?id=${courseTypeId}`, {
                headers: { 'userToken': 'wasd' }
            });
            const resp = response.data;
            return resp.courseName;
        } catch (e) {
            errorType = e;
            console.error(e);
            throw e;
        }
    }
    /**
     * Handles the check button click event.
     * @param {number} courseId - The type of course to be approved.
     * @returns {Promise<string[]>} - The ID of the clicked item.
     */
    async function getSessionsStart(courseId) {
        try {
            const response = await AXIOS.get(`/courseSessions/getByOffering?courseOfferingId=${courseId}`, {
            });
            const answer = [];
            for (let i = 0; i < response.data.length; i++){
                answer.push(response.data[i].startTime);
            }
            return answer;
        } catch (e) {
            errorType = e;
            console.error(e);
            throw e;
        }
    }
    /**
     * Handles the check button click event.
     * @param {number} courseId - The type of course to be approved.
     * @returns {Promise<string[]>} - The ID of the clicked item.
     */
    async function getSessionsEnd(courseId) {
        try {
            const response = await AXIOS.get(`/courseSessions/getByOffering?courseOfferingId=${courseId}`, {
            });
            const answer = [];
            for (let i = 0; i < response.data.length; i++){
                answer.push(response.data[i].endTime);
                //answer.push(" " + (i + 1) + " - " + response.data[i].endTime);
            }
            return answer;
        } catch (e) {
            errorType = e;
            console.error(e);
            throw e;
        }
    }
    /**
     * Handles the check button click event.
     * @param {number} courseId - The type of course to be approved.
     * @returns {Promise<string[]>} - The ID of the clicked item.
     */
    async function getSessionsDate(courseId) {
        try {
            const response = await AXIOS.get(`/courseSessions/getByOffering?courseOfferingId=${courseId}`, {
            });
            const answer = [];
            for (let i = 0; i < response.data.length; i++){
                answer.push(response.data[i].date);
                //answer.push(" " + (i + 1) + " - " + response.data[i].endTime);
            }
            return answer;
        } catch (e) {
            errorType = e;
            console.error(e);
            throw e;
        }
    }

    /**
     * Handles the check button click event.
     * @param {string} taughtCourse - The type of course to be approved.
     * @returns {void} - The ID of the clicked item.
     */
    function handleCourseClick(taughtCourse){
        selectedCourse = taughtCourse;
        const index = taughtCourses.indexOf(taughtCourse);
        startDate = taughtCoursesStartDate[index];
        endDate = taughtCoursesEndDate[index];
        daysOffered = taughtCoursesDaysOffered[index]
        startTime = taughtCoursesSessionsStart[index];
        endTime =  taughtCoursesSessionsEnd[index];
        sessionDate = taughtCoursesSessionsDate[index];
        /*
        monday = daysOffered.includes('MONDAY');
        tuesday = daysOffered.includes('TUESDAY');
        wednesday = daysOffered.includes('WEDNESDAY');
        thursday = daysOffered.includes('THURSDAY');
        friday = daysOffered.includes('FRIDAY');
        saturday = daysOffered.includes('SATURDAY');
        sunday = daysOffered.includes('SUNDAY');*/
    }

    onMount(async () => {
        AXIOS.get('courseOfferings/getByInstructor',{
            headers:{
                'userToken': 'dsaw'
            }
        })
            .then(async response => {
                const courses = response.data;
                const coursesList = [];
                const courseListStartDate = [];
                const courseListEndDate = [];
                const coursesListID = [];
                const coursesListDaysOffered = [];
                const coursesListSessionsStart = [];
                const coursesListSessionsEnd = [];
                const coursesListSessionsDate = [];


                for (let i = 0; i < courses.length; i++) {
                    const course = courses[i];
                    const courseName = await getCourseName(course.courseTypeId);
                    const courseSessionsStart = await getSessionsStart(course.id);
                    const courseSessionsEnd = await getSessionsEnd(course.id);
                    const courseSessionsDate = await getSessionsDate(course.id);

                    coursesList.push(courseName);
                    courseListStartDate.push(course.startDate);
                    courseListEndDate.push(course.endDate);
                    coursesListID.push(course.id);
                    coursesListDaysOffered.push(course.daysOffered);
                    coursesListSessionsStart.push(courseSessionsStart);
                    coursesListSessionsEnd.push(courseSessionsEnd);
                    coursesListSessionsDate.push(courseSessionsDate);

                }

                taughtCourses = coursesList; // Update items array with the new values
                taughtCoursesStartDate = courseListStartDate;
                taughtCoursesEndDate = courseListEndDate;
                taughtCoursesID = coursesListID;
                taughtCoursesDaysOffered = coursesListDaysOffered;
                taughtCoursesSessionsStart = coursesListSessionsStart;
                taughtCoursesSessionsEnd = coursesListSessionsEnd;
                taughtCoursesSessionsDate = coursesListSessionsDate;

            })
            .catch(e => {
                errorType = e;
            });

        AXIOS.get('/courseTypes/getAll',{
            headers:{
                'userToken': 'dsaw'
            }
        })
            .then(response => {
                const types = response.data;
                const updatedItems = [];
                const updatedItemsId = []
                const updatedItems1 = [];
                const updatedItems1Id = []

                for (let i = 0; i < types.length; i++) {
                    const type = types[i];
                    if (type.approvalStatus === true) {
                        updatedItems1.push(type.courseName);
                        updatedItems1Id.push(type.id);
                    }
                }

                approvedTypes = updatedItems1; // Update items array with the new values
                approvedTypesID = updatedItems1Id;
            })
            .catch(e => {
                errorType = e;
            });
    });
</script>

<div class="grid-container">
    <div class="component-container component-1">
        <div class="list-container-left">
            <div class="bg-secondary/20 list-header-left-bg"> <!-- Background wrapper for list header -->
                <h1 class="list-header-left">Taught Courses</h1>
            </div>
            <div class="scrollable-list-left">
                <!-- Render list items -->
                {#each taughtCourses as taughtCourse}
                    <div class="bg-secondary-content/5 list-item-left" on:click={() => handleCourseClick(taughtCourse)}>
                        {taughtCourse}
                    </div>
                {/each}
            </div>
            <div class="button-container">
                <button class="btn" on:click={() => {if (!showUpdateContentInfo) toggleUpdateContentInfo()}}>Create New Course</button>
            </div>
        </div>
    </div>
    <div class="component-container component-2">
        {#if showUpdateContentInfo}
            <!-- Content shown when update button is clicked -->
            <div class="list-container-left">
                <div class="bg-secondary/20 list-header-left-bg"> <!-- Background wrapper for list header -->
                    <h1 class="list-header-left">
                        Create New Course
                    </h1>
                </div>
                <!-- Input fields for Start Date and End Date -->
                <div class="bg-secondary-content/5 input-container">
                    <label for="start-date">Start Date:</label>
                    <input class="bg-secondary-content/5" type="date" id="start-date" name="start-date" bind:value={startDateNew}>
                </div>
                <div class="bg-secondary-content/5 input-container">
                    <label for="end-date">End Date:</label>
                    <input class="bg-secondary-content/5" type="date" id="end-date" name="end-date" bind:value={endDateNew}>
                </div>
                <div class="bg-secondary-content/5 input-container">
                    <label for="price">Price:</label>
                    <input class="bg-secondary-content/5" type="number" id="price" name="price" bind:value={priceNew}>
                </div>
                <!-- Input fields for Floor Number and Room Number -->
                <div class="bg-secondary-content/5 input-container">
                    <label for="floor-number">Floor Number:</label>
                    <input class="bg-secondary-content/5" type="number" id="floor-number" name="floor-number" bind:value={floorNumberNew} min="1">
                </div>
                <div class="bg-secondary-content/5 input-container">
                    <label for="room-number">Room Number:</label>
                    <input class="bg-secondary-content/5" type="number" id="room-number" name="room-number" bind:value={roomNumberNew} min="1">
                </div>
                <!-- Input fields for Days Offered -->
                <div class="bg-secondary-content/5 input-container-checkboxes">
                    <label>Days Offered:</label>
                    <div class="days-offered-container">
                        <label>M <input type="checkbox" class="checkbox checkbox-styling" on:change={() => updateDaysOffered('MONDAY')}></label>
                        <label>Tu <input type="checkbox" class="checkbox checkbox-styling" on:change={() => updateDaysOffered('TUESDAY')}></label>
                        <label>W <input type="checkbox" class="checkbox checkbox-styling" on:change={() => updateDaysOffered('WEDNESDAY')}></label>
                        <label>Th <input type="checkbox" class="checkbox checkbox-styling" on:change={() => updateDaysOffered('THURSDAY')}></label>
                        <label>F <input type="checkbox" class="checkbox checkbox-styling" on:change={() => updateDaysOffered('FRIDAY')}></label>
                        <label>Sa <input type="checkbox" class="checkbox checkbox-styling" on:change={() => updateDaysOffered('SATURDAY')}></label>
                        <label>Su <input type="checkbox" class="checkbox checkbox-styling" on:change={() => updateDaysOffered('SUNDAY')}></label>
                    </div>
                </div>
                <!-- Dropdown for Approved Types -->
                <div class="bg-secondary-content/5 input-container">
                    <label for="approved-types-dropdown">Course Type:</label>
                    <select class="bg-secondary-content/5" id="approved-types-dropdown" bind:value={selectedType}>
                        {#each approvedTypes as type}
                            <option value={type}>{type}</option>
                        {/each}
                    </select>
                </div>
            </div>
        {:else}
        <!-- Default content -->
            <div class="list-container-left">
                <div class="bg-secondary/20 list-header-left-bg"> <!-- Background wrapper for list header -->
                    <h1 class="list-header-left">
                        {#if selectedCourse !== ""}
                            {selectedCourse}
                        {:else}
                            <!-- Display something when nothing is hovered over -->
                            No course selected
                        {/if}
                    </h1>
                </div>
                <div class="bg-secondary-content/5 info-blocks">
                    Start Date: {startDate}
                </div>
                <div class="bg-secondary-content/5 info-blocks">
                    End Date: {endDate}
                </div>
                <div class="bg-secondary-content/5 info-blocks">
                    Days Offered: {daysOffered}
                </div>
            </div>
        {/if}
    </div>
    <div class="component-container component-3">
        <div class="list-container-left">
            <div class="bg-secondary/20 list-header-left-bg">
                <h1 class="list-header-left">
                    {#if selectedCourse !== ""}
                        {selectedCourse} Sessions
                    {:else}
                        No course selected
                    {/if}
                </h1>
            </div>
            <div class="scrollable-list-right">
                {#if startTime.length === endTime.length}
                    {#each startTime as time, index}
                        <div class="bg-secondary-content/5 info-blocks">
                            <div>Date: {sessionDate[index]}</div>
                            <div>Start Time: {time}</div>
                            <div>End Time: {endTime[index]}</div>
                        </div>
                    {/each}
                {:else}
                    <div class="bg-secondary-content/5 info-blocks">
                        Error: Start time and end time arrays have different lengths.
                    </div>
                {/if}
            </div>
        </div>
        <div class="button-container">
            <button class="btn">Update</button>
        </div>
    </div>
</div>

<style>
    .grid-container {
        display: grid;
        grid-template-columns: repeat(4, 1fr); /* Three columns with equal width */
        grid-template-rows: repeat(5, 1fr); /* Three rows with equal height */
        gap: 10px; /* Gap between grid items */
        padding-top: 0.5vh;
        padding-bottom: 10px;
        height: 90%; /* Occupy full height of the main element */
        width: 100%;
    }

    .component-container {
        border: 2px solid #ccc; /* Add border for visualization */
        border-radius: 10px;
        display: flex; /* Use flexbox */
        align-items: center; /* Center vertically */
        width: 100%; /* Ensure component fills up the whole cell horizontally */
        height: 100%; /* Ensure component fills up the whole cell vertically */
    }

    .component-1 {
        grid-column: span 2; /* Span two columns */
        grid-row: span 5; /* Span three rows */
        position: relative;
    }

    .component-2 {
        grid-column: span 2; /* Third column */
        grid-row: span 3; /* Span three rows */
        position: relative;

    }
    .component-3 {
        grid-column: span 2; /* Third column */
        grid-row: span 2; /* Span three rows */
        position: relative;
    }

    /* Add CSS styles for the list container */
    .list-container-left {
        padding-top: 1%; /* Add padding to the top of the container */
        padding-bottom: 1%; /* Add padding to the bottom of the container */
        width: 100%;
        height: 100%;
    }

    /* Add CSS styles for the scrollable list */
    .scrollable-list-left {
        max-height: 60vh; /* Limit the height of the list */
        max-width: 100%;
        margin-bottom: 1vh;
        overflow-y: auto; /* Enable vertical scrolling */
        border-radius: 10px; /* Rounded corners */
        padding-left: 10px ;
        padding-right: 10px ;
        display: flex; /* Use flexbox */
        flex-direction: column; /* Arrange items vertically */
        align-items: flex-start; /* Center items horizontally */
        list-style-type: none; /* Remove list-style (bullets) */
    }
    /* Add CSS styles for the scrollable list */
    .scrollable-list-right {
        max-height: 65%; /* Limit the height of the list */
        max-width: 100%;
        margin-bottom: 1vh;
        overflow-y: auto; /* Enable vertical scrolling */
        border-radius: 10px; /* Rounded corners */
        padding-left: 10px ;
        padding-right: 10px ;
        display: flex; /* Use flexbox */
        flex-direction: column; /* Arrange items vertically */
        align-items: flex-start; /* Center items horizontally */
        list-style-type: none; /* Remove list-style (bullets) */
    }

    /* Style for list items */
    .list-item-left {
        margin-bottom: 5px;
        border-radius: 10px; /* Rounded corners for the highlight box */
        padding: 5px; /* Add left padding to create space */
        transition: background-color 0.3s; /* Smooth transition for hover effect */
        width: 100%; /* Ensure header spans the full width */
        display: flex; /* Use flexbox */
        justify-content: space-between; /* Align content on each end */
        align-items: center; /* Center items vertically */
        font-size: 2.1vh;
    }

    /* Apply background color when hovering over list item */
    .list-item-left:hover {
        font-size: 2.4vh; /* Increase font size */
    }

    /* Style for list header */
    .list-header-left {
        font-size: 2.7vh;
        text-align: center; /* Center the header text horizontally */
        width: 100%; /* Ensure header spans the full width */
    }

    .list-header-left-bg {
        width: 75%; /* Set the width to occupy 75% of the space */
        margin: 0 auto; /* Center the header horizontally */
        margin-top: 10px;
        margin-bottom: 10px;
        border-radius: 5px; /* Rounded corners */
    }

    .info-blocks {
        width: 98%;
        border-radius: 10px;
        margin: 5px;
        padding: 5px;
        font-size: 2.1vh;
    }

    .input-container {
        width: 98%;
        border-radius: 10px;
        margin: 5px;
        padding: 5px;
        font-size: 2.1vh;
    }


    .button-container {
        position: absolute; /* Position the button container */
        bottom: 10px; /* Adjust as needed */
        right: 10px; /* Align with the right edge of the parent container */
        width: auto; /* Allow the button container to adjust its width based on content */
        text-align: right; /* Align the button to the right */
    }

    .btn {
        margin-left: 10px; /* Add margin between the button and other elements */
    }

    .input-container-checkboxes {
        display: flex;
        align-items: center;
        width: 98%;
        border-radius: 10px;
        margin: 5px;
        padding: 5px;
        font-size: 2.1vh;
    }

    .days-offered-container {
        display: flex;
        align-items: center;
        margin-left: 10px; /* Adjust margin as needed */
    }

    .checkbox-styling {
        height: 2vh;
    }

</style>